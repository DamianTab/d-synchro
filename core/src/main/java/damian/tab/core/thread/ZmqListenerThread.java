package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import org.zeromq.ZContext;
import org.zeromq.ZPoller;

import java.util.ArrayList;
import java.util.List;

abstract class ZmqListenerThread implements Runnable, AutoCloseable {
    protected final ZContext zContext;
    protected final ZPoller zPoller;
    protected final List<SocketProxy> subscriptions;
    protected final SocketProxy publisher;

    public ZmqListenerThread(ZContext zContext, SocketProxy publisher) {
        this.zContext = zContext;
        this.publisher = publisher;
        this.zPoller = new ZPoller(zContext);
        this.subscriptions = new ArrayList<>();
    }

    @Override
    public void close() {
        subscriptions.forEach(SocketProxy::close);
        publisher.close();
    }

    protected void registerSocket(SocketProxy socketProxy){
        this.zPoller.register(socketProxy.getSocket(), ZPoller.POLLIN);
    }
}
