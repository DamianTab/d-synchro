package damian.tab.core.thread;

import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.task.DistributedTaskInterface;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;

@Slf4j
public class DistributedThread implements Runnable, AutoCloseable {

    private final DistributedTaskInterface distributedTask;
    private final ClientListenerRunnable clientListenerThread;
    private final RicartAgrawalaExecutor algorithmExecutor;

    /**
     * @param distributedTask      Runnable interface with task to execute
     * @param clientListenerThread
     * @param algorithmExecutor
     */
    DistributedThread(DistributedTaskInterface distributedTask, ClientListenerRunnable clientListenerThread, RicartAgrawalaExecutor algorithmExecutor) {
        this.distributedTask = distributedTask;
        this.clientListenerThread = clientListenerThread;
        this.algorithmExecutor = algorithmExecutor;
        distributedTask.assignClientListener(clientListenerThread);
        distributedTask.assignAlgorithmExecutor(algorithmExecutor);
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
