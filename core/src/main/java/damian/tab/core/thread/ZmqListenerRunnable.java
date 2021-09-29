package damian.tab.core.thread;

import damian.tab.core.zmq.SocketProxy;
import damian.tab.core.zmq.SocketProxyHandler;
import lombok.Getter;
import org.zeromq.ZContext;
import org.zeromq.ZPoller;

abstract class ZmqListenerRunnable implements Runnable, AutoCloseable {
    protected final SocketProxyHandler proxyHandler;
    protected final ZContext zContext;
    protected final ZPoller zPoller;
    @Getter
    protected final SocketProxy publisher;

    public ZmqListenerRunnable(SocketProxyHandler proxyHandler, ZContext zContext, SocketProxy publisher) {
        this.proxyHandler = proxyHandler;
        this.zContext = zContext;
        this.publisher = publisher;
        this.zPoller = new ZPoller(zContext);
    }

    @Override
    public void close() {
        publisher.close();
    }

    protected void registerSocket(SocketProxy socketProxy) {
        this.zPoller.register(socketProxy.getSocket(), ZPoller.POLLIN);
    }
}
