package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import org.zeromq.ZContext;

class ClientZmqThread extends ZmqListenerThread{

    private final SocketProxy initializationRequester;
    private SocketProxy portMapperSubscriber;

    public ClientZmqThread(ZContext zContext, SocketProxy publisher, SocketProxy initializationRequester) {
        super(zContext, publisher);
        this.initializationRequester = initializationRequester;
    }

    public void connectToPortMapper(){
        //todo tutaj zrobic to łączenie
        initializationRequester.close();
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
