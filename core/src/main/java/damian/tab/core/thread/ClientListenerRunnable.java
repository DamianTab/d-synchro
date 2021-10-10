package damian.tab.core.thread;

import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.proto.InitResponseMessage;
import damian.tab.core.proto.NewConnectionMessage;
import damian.tab.core.thread.model.ProcessData;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyBuilderService;
import damian.tab.core.zmq.SocketProxyHandler;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientListenerRunnable extends ZmqListenerRunnable {

    private final RicartAgrawalaExecutor algorithmExecutor;
    private final List<SocketProxy> subscriptions;
    private final SocketProxy initializationRequester;

    @Getter
    private SocketProxy portMapperSubscriber;
    @Getter
    private ProcessData processData;

    public ClientListenerRunnable(RicartAgrawalaExecutor algorithmExecutor, SocketProxyHandler proxyHandler, ZContext zContext, SocketProxy publisher, SocketProxy initializationRequester) {
        super(proxyHandler, zContext, publisher);
        this.algorithmExecutor = algorithmExecutor;
        this.initializationRequester = initializationRequester;
        this.subscriptions = new ArrayList<>();
    }

    @Override
    public void run() {
        log.info("Started ClientListenerRunnable thread.");

        while (!Thread.interrupted()) {
            zPoller.poll(-1L);
//        New Client info from PortMapper
            if (zPoller.isReadable(portMapperSubscriber.getSocket())) {
                NewConnectionMessage newConnectionMessage = (NewConnectionMessage) proxyHandler.receive(portMapperSubscriber);
                if (isNotThisAndNotInSubscriptions(newConnectionMessage.getAddress())) {
                    addNewSubscriberAndRegister(newConnectionMessage.getAddress());
                }
            }
//            Handle SynchroMessage - lock/unlock or wait/notify from other clients
            subscriptions.forEach(subscriber -> {
                if (zPoller.isReadable(subscriber.getSocket())) {
                    algorithmExecutor.handleSynchroMessage(this, subscriber);
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

    void initializeProcessWithPortMapper() {
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
        responseMessage = (InitResponseMessage) proxyHandler.receive(initializationRequester);
        log.info("Received response from portmapper: {}", responseMessage);
        responseMessage.getAddressesList().forEach(address -> {
            if (isNotThisAndNotInSubscriptions(address)){
                addNewSubscriberAndRegister(address);
            }
        });
        log.info("Successfully initialized ClientListenerRunnable.");
        initializationRequester.close();
    }

    private void sendRequestMessageToPortMapper(boolean confirmingMessage) {
        InitRequestMessage requestMessage = InitRequestMessage.newBuilder()
                .setAddress(publisher.getAddress())
                .setReady(confirmingMessage)
                .build();
        proxyHandler.send(initializationRequester, requestMessage);
    }

    private void configureProcessIdAndSubscribersFromMessage(InitResponseMessage responseMessage) {
        processData = new ProcessData(responseMessage.getProcessID());
        portMapperSubscriber = SocketProxyBuilderService.createSubscriber(zContext, responseMessage.getPortMapperAddress());
        registerSocket(portMapperSubscriber);
        responseMessage.getAddressesList().forEach(this::addNewSubscriberAndRegister);
    }

    private void addNewSubscriberAndRegister(String address) {
        synchronized (processData){
            processData.getClock().add(0);
        }
        SocketProxy subscriber = SocketProxyBuilderService.createSubscriber(zContext, address);
        subscriptions.add(subscriber);
        registerSocket(subscriber);
        log.info("Added new client-subscriber with address {} , clock now is {}", address, processData.GetThreadSafeClock()) ;
    }

    private boolean isNotThisAndNotInSubscriptions(String address) {
        return !address.equals(publisher.getAddress())
                && subscriptions.stream()
                .map(SocketProxy::getAddress)
                .noneMatch(socketAddress -> socketAddress.equals(address));
    }
}
