package damian.tab.core.thread;

import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.proto.InitResponseMessage;
import damian.tab.core.proto.NewConnectionMessage;
import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyHandler;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;
import org.zeromq.ZPoller;

import java.util.ArrayList;
import java.util.List;

//todo PortMapper also informs about disconnecting client
@Slf4j
public class PortMapperListenerRunnable extends ZmqListenerRunnable {
    private final SocketProxy initializationReplayer;
    private final List<String> clientAddresses;
    private int counter = 0;

    public PortMapperListenerRunnable(SocketProxyHandler proxyHandler, ZContext zContext, SocketProxy publisher, SocketProxy initializationReplayer) {
        super(proxyHandler, zContext, publisher);
        this.initializationReplayer = initializationReplayer;
        clientAddresses = new ArrayList<>();
    }

    @Override
    public void run() {
        log.info("Started PortMapperListenerRunnable thread.");
        zPoller.register(initializationReplayer.getSocket(), ZPoller.POLLIN);

        while (!Thread.interrupted()) {
            zPoller.poll(-1L);
//        New Client
            while (zPoller.isReadable(initializationReplayer.getSocket())) {
                InitRequestMessage requestMessage = (InitRequestMessage) proxyHandler.receive(initializationReplayer);
                handleNewClientMessage(requestMessage);
            }
        }
    }

    @Override
    public void close() {
        super.close();
        initializationReplayer.close();
        log.info("Closed PortMapperListenerRunnable.");
    }

    private void handleNewClientMessage(InitRequestMessage requestMessage) {
        if (requestMessage.getReady()) {
            clientAddresses.add(requestMessage.getAddress());
            NewConnectionMessage message = NewConnectionMessage.newBuilder()
                    .setAddress(requestMessage.getAddress())
                    .build();
            proxyHandler.send(publisher, message);
            proxyHandler.send(initializationReplayer, message);
            log.info("Added new client with address: {}", requestMessage.getAddress());
        } else {
            InitResponseMessage message = InitResponseMessage.newBuilder()
                    .setProcessID(counter++)
                    .setPortMapperAddress(publisher.getAddress())
                    .addAllAddresses(clientAddresses)
                    .build();
            proxyHandler.send(initializationReplayer, message);
        }
    }
}
