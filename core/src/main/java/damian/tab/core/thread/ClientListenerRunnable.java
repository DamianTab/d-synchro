package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;

@Slf4j
public class ClientListenerRunnable extends ZmqListenerRunnable {

    private final SocketProxy initializationRequester;
    private SocketProxy portMapperSubscriber;

    public ClientListenerRunnable(ZContext zContext, SocketProxy publisher, SocketProxy initializationRequester) {
        super(zContext, publisher);
        this.initializationRequester = initializationRequester;
    }

    public void initialize(){
        //todo tutaj zrobic to łączenie
    }

    @Override
    public void run() {

    }

    @Override
    public void close() {
        super.close();
        if (portMapperSubscriber != null){
            portMapperSubscriber.close();
        }
        log.info("Closed ClientListenerRunnable.");
    }
}
