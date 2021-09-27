package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import org.zeromq.ZContext;

class ClientZmqThread extends ZmqListenerThread{

    private SocketProxy portMapperSubscriber;

    public ClientZmqThread(ZContext zContext, SocketProxy publisher) {
        super(zContext, publisher);
    }

    @Override
    public void run() {

    }

    @Override
    public void close() {
        super.close();
        portMapperSubscriber.close();
    }
}
