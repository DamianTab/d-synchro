package damian.tab.core.thread;

import lombok.extern.slf4j.Slf4j;
import org.zeromq.ZContext;
@Slf4j
public final class DistributedThread extends Thread {

    private final ZContext zContext;

    /**
     * Constructor for non-spring users. For Spring users bean -> @DistributedThreadFactory
     *
     * @param target Runnable interface with task to execute
     */
    public DistributedThread(Runnable target) {
        super(target);
        this.zContext = new ZContext();
    }

    /**
     * Constructor for Spring users
     *
     * @param target   Runnable interface with task to execute
     * @param zContext Context for zmq
     */
    DistributedThread(Runnable target, ZContext zContext) {
        super(target);
        this.zContext = zContext;
    }

    @Override
    public synchronized void start() {
        log.info("Starting Distributed Thread -- {}", this.getName());
        super.start();
    }
}
