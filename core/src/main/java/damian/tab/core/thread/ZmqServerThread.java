package damian.tab.core.thread;

import org.zeromq.ZContext;
import org.zeromq.ZPoller;

public class ZmqServerThread implements Runnable {
    private final ZContext zContext;
    private final ZPoller zPoller;

    public ZmqServerThread(ZContext zContext) {
        this.zContext = zContext;
        this.zPoller = new ZPoller(zContext);
    }

    @Override
    public void run() {
//        todo tutaj dac wysyłanie do portmapper


//        todo tutaj zrobic odbieranie w petli i dać pollera lub w distributed thread

    }
}
