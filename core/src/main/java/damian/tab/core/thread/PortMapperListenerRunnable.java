package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;

@Slf4j
public class PortMapperListenerRunnable extends ZmqListenerRunnable {
    private final SocketProxy initializationReplayer;

    public PortMapperListenerRunnable(ZContext zContext, SocketProxy publisher, SocketProxy initializationReplayer) {
        super(zContext, publisher);
        this.initializationReplayer = initializationReplayer;
    }

    @Override
    public void run() {

    }

    @Override
    public void close() {
        super.close();
        initializationReplayer.close();
        log.info("Closed PortMapperListenerRunnable.");
    }
}
