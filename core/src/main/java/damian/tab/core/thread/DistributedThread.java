package damian.tab.core.thread;

import damian.tab.core.task.DistributedTask;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class DistributedThread implements Runnable, AutoCloseable {

    private final DistributedTask distributedTask;
    private final ClientListenerRunnable clientListenerThread;

    /**
     *
     * @param distributedTask Runnable interface with task to execute
     * @param clientListenerThread
     */
    DistributedThread(DistributedTask distributedTask, ClientListenerRunnable clientListenerThread) {
        this.distributedTask = distributedTask;
        this.clientListenerThread = clientListenerThread;
        distributedTask.assignClientListener(clientListenerThread);
    }

    @Override
    public void run() {
        log.info("Starting Distributed Thread.");
        clientListenerThread.initializeProcessWithPortMapper();
        //todo graceful shutdown
        Executors.newSingleThreadExecutor().execute(clientListenerThread);
        distributedTask.run();
    }

    @Override
    public void close() {
        clientListenerThread.close();
        log.info("Closed Distributed Thread.");
    }
}
