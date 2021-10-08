package damian.tab.core.task;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.monitor.algorithm.RequestShepherd;
import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.thread.ClientListenerRunnable;

public interface DistributedTaskInterface extends Runnable {
    void assignNecessaryServices(ClientListenerRunnable clientListener, RicartAgrawalaExecutor algorithmExecutor, RequestShepherd requestShepherd);

    DistributedMonitor createNewMonitor(String monitorId);
}
