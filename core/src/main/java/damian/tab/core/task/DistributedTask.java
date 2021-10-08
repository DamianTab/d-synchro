package damian.tab.core.task;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.monitor.algorithm.RequestShepherd;
import damian.tab.core.monitor.algorithm.RicartAgrawalaExecutor;
import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class DistributedTask implements DistributedTaskInterface {

    private ClientListenerRunnable clientListenerRunnable;
    private RicartAgrawalaExecutor algorithmExecutor;
    private RequestShepherd requestShepherd;

    @Override
    public void assignNecessaryServices(ClientListenerRunnable clientListener, RicartAgrawalaExecutor algorithmExecutor, RequestShepherd requestShepherd) {
        this.clientListenerRunnable = clientListener;
        this.algorithmExecutor = algorithmExecutor;
        this.requestShepherd = requestShepherd;
    }

    @Override
    public DistributedMonitor createNewMonitor(String monitorId) {
        return new DistributedMonitor(monitorId, clientListenerRunnable, algorithmExecutor, requestShepherd);
    }
}
