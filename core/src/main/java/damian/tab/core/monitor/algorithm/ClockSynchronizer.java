package damian.tab.core.monitor.algorithm;

import damian.tab.core.thread.model.ProcessData;
import org.springframework.stereotype.Service;

@Service
public class ClockSynchronizer {
    public void expandClock(ProcessData processData){
        processData.getClock().add(0);
    }
}
