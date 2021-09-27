package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import org.zeromq.ZContext;

public class PortMapperZmqThread extends ZmqListenerThread{
    private final SocketProxy initializationReplayer;

    public PortMapperZmqThread(ZContext zContext, SocketProxy publisher, SocketProxy initializationReplayer) {
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
    }
}
