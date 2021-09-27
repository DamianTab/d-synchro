package damian.tab.core.thread;

import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;
@Slf4j
public final class DistributedThread extends Thread {

    private final ZContext zContext;
    private final ClientZmqThread clientZmqThread;

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
     * @param clientZmqThread
     */
    DistributedThread(Runnable target, ZContext zContext, ClientZmqThread clientZmqThread) {
        super(target);
        this.zContext = zContext;
        this.clientZmqThread = clientZmqThread;
    }

    @Override
    public synchronized void start() {
        log.info("Starting Distributed Thread -- {}", this.getName());
        log.info("Created ClientZmqThread", this.getName());

        super.start();
    }
}
