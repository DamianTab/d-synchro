package damian.tab.core.thread;

import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.proto.InitResponseMessage;
import damian.tab.core.proto.NewConnectionMessage;
import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyHandler;
import damian.tab.core.zmq.SocketProxySubscriberInitializer;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientListenerRunnable extends ZmqListenerRunnable {

    private final SocketProxyHandler proxyHandler;
    private final List<SocketProxy> subscriptions;
    private final SocketProxy initializationRequester;

    private SocketProxy portMapperSubscriber;
    private int processId;

    public ClientListenerRunnable(ZContext zContext, SocketProxy publisher, SocketProxyHandler proxyHandler, SocketProxy initializationRequester) {
        super(zContext, publisher);
        this.proxyHandler = proxyHandler;
        this.initializationRequester = initializationRequester;
        this.subscriptions = new ArrayList<>();
    }

    //todo throw error if address have been already taken
    public void initializeProcessWithPortMapper() {
        log.info("Initialize ClientListenerRunnable with PortMapper.");
//        Send 1st message
        sendRequestMessageToPortMapper(false);
//        Receive PortMapper message with process configuration
        InitResponseMessage responseMessage = (InitResponseMessage) proxyHandler.receive(initializationRequester);
        log.info("Received response from portmapper: {}", responseMessage);
        configureProcessIdAndSubscribersFromMessage(responseMessage);
//        Send 2nd message to confirm
        sendRequestMessageToPortMapper(true);
//        Confirm from PortMapper to end 4-way handshake
        NewConnectionMessage newConnectionMessage = (NewConnectionMessage) proxyHandler.receive(initializationRequester);
        if (!newConnectionMessage.getAddress().equals(publisher.getAddress())) {
            throw new RuntimeException("Receive wrong address from PortMapper - fault in portMapper configuration.");
        }
        log.info("Successfully initialized ClientListenerRunnable.");
        initializationRequester.close();
    }

    @Override
    public void run() {
        log.info("Started ClientListenerRunnable thread.");

        while (!Thread.interrupted()) {
            zPoller.poll(-1L);
//        New Client info from PortMapper
            while (zPoller.isReadable(portMapperSubscriber.getSocket())) {
                NewConnectionMessage newConnectionMessage = (NewConnectionMessage) proxyHandler.receive(portMapperSubscriber);
                if (!newConnectionMessage.getAddress().equals(publisher.getAddress())) {
                    addNewSubscriberAndRegister(newConnectionMessage.getAddress());
                }
            }
//            Handle SynchroMessage - lock/unlock or wait/notify from other clients
            subscriptions.forEach(subscriber -> {
                while (zPoller.isReadable(subscriber.getSocket())) {
                    SynchroMessage synchroMessage = (SynchroMessage)  proxyHandler.receive(subscriber);
                    log.info("Received synchro message: {}", synchroMessage);
                    //                    todo handle ricart-agrawali

                }
            });
        }

    }

    @Override
    public void close() {
        super.close();
        if (portMapperSubscriber != null) {
            portMapperSubscriber.close();
        }
        subscriptions.forEach(SocketProxy::close);
        log.info("Closed ClientListenerRunnable.");
    }

    private void sendRequestMessageToPortMapper(boolean confirmingMessage) {
        InitRequestMessage requestMessage = InitRequestMessage.newBuilder()
                .setAddress(publisher.getAddress())
                .setReady(confirmingMessage)
                .build();
        proxyHandler.send(initializationRequester, requestMessage);
    }

    private void configureProcessIdAndSubscribersFromMessage(InitResponseMessage responseMessage) {
        processId = responseMessage.getProcessID();
        portMapperSubscriber = SocketProxySubscriberInitializer.createSubscriber(zContext, responseMessage.getPortMapperAddress());
        this.registerSocket(portMapperSubscriber);
        responseMessage.getAddressesList().forEach(this::addNewSubscriberAndRegister);
    }

    public void addNewSubscriberAndRegister(String address) {
        SocketProxy subscriber = SocketProxySubscriberInitializer.createSubscriber(zContext, address);
        subscriptions.add(subscriber);
        this.registerSocket(subscriber);
        log.info("Added new client-subscriber with address {}", address);
    }
}
