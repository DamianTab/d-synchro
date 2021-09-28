package damian.tab.core.thread;

import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.proto.InitResponseMessage;
import damian.tab.core.proto.NewConnectionMessage;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxySubscriberInitializer;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ClientListenerRunnable extends ZmqListenerRunnable {

    protected final List<SocketProxy> subscriptions;
    private final SocketProxy initializationRequester;
    private SocketProxy portMapperSubscriber;
    private int processId;

    public ClientListenerRunnable(ZContext zContext, SocketProxy publisher, SocketProxy initializationRequester) {
        super(zContext, publisher);
        this.initializationRequester = initializationRequester;
        this.subscriptions = new ArrayList<>();
    }

    public void initializeProcessWithPortMapper() {
        log.info("Initialize ClientListenerRunnable with PortMapper.");
//        Send 1st message
        sendRequestMessageToPortMapper(false);
//        Receive PortMapper message with process configuration
        InitResponseMessage responseMessage = (InitResponseMessage) initializationRequester.receive();
        log.info("Received response from portmapper: {}", responseMessage);
        configureProcessIdAndSubscribers(responseMessage);
//        Send 2nd message to confirm
        sendRequestMessageToPortMapper(true);
//        Confirm from PortMapper to end 4-way handshake
        NewConnectionMessage newConnectionMessage = (NewConnectionMessage) initializationRequester.receive();
        if (!newConnectionMessage.getAddress().equals(publisher.getAddress())) {
            throw new RuntimeException("Receive wrong address from PortMapper - fault in portMapper configuration.");
        }
        log.info("Successfully initialized ClientListenerRunnable.");
        initializationRequester.close();
    }

    @Override
    public void run() {
        log.info("Started ClientListenerRunnable thread.");
        //todo tutaj zrobic nasluchiwanie klienta

//        todo odbierajac swoj adres z portmappera powinien ignorowac
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
        initializationRequester.send(requestMessage);

    }

    private void configureProcessIdAndSubscribers(InitResponseMessage responseMessage) {
        processId = responseMessage.getProcessID();
        portMapperSubscriber = SocketProxySubscriberInitializer.createSubscriber(zContext, responseMessage.getPortMapperAddress());
        this.registerSocket(portMapperSubscriber);
        responseMessage.getAddressesList().forEach(address -> {
            SocketProxy subscriber = SocketProxySubscriberInitializer.createSubscriber(zContext, address);
            this.registerSocket(subscriber);
        });
    }
}
