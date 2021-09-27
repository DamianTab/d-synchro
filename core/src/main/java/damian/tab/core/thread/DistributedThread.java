package damian.tab.core.thread;

import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;
@Slf4j
public final class DistributedThread extends Thread implements AutoCloseable {

    private final ZContext zContext;
    private final ClientListenerRunnable clientListenerThread;

//    /**
//     * Constructor for non-spring users. For Spring users bean -> @DistributedThreadFactory
//     *
//     * @param target Runnable interface with task to execute
//     */
//    public DistributedThread(Runnable target) {
//        super(target);
//        this.zContext = new ZContext();
//    }

    /**
     * Constructor for Spring users
     *  @param target   Runnable interface with task to execute
     * @param zContext Context for zmq
     * @param clientListenerThread
     */
    DistributedThread(Runnable target, ZContext zContext, ClientListenerRunnable clientListenerThread) {
        super(target);
        this.zContext = zContext;
        this.clientListenerThread = clientListenerThread;
    }

    @Override
    public synchronized void start() {
        log.info("Starting Distributed Thread -- {}", this.getName());
        clientListenerThread.initialize();
        super.start();
    }

    @Override
    public void close() {
        clientListenerThread.close();
        log.info("Closed {}", this.getName());
    }
}
