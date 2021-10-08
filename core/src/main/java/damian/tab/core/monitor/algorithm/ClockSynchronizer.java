package damian.tab.core.monitor.algorithm;

import damian.tab.core.thread.model.ProcessData;
import org.springframework.stereotype.Service;

@Service
public class ClockSynchronizer {

    public void expandClock(ProcessData processData) {
        synchronized (processData) {
            processData.getClock().add(0);
        }
    }

    public void incrementClock(ProcessData processData) {
        int processId = processData.getProcessId();
        synchronized (processData) {
            int oldValue = processData.getClock().get(processId);
            processData.getClock().set(processId, oldValue + 1);
        }
    }
}
