package damian.tab.core.monitor.algorithm;

import damian.tab.core.thread.model.ProcessData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class ClockSynchronizer {

    public void incrementClock(ProcessData processData) {
        int processId = processData.getProcessId();
        synchronized (processData) {
            int oldValue = processData.getClock().get(processId);
            processData.getClock().set(processId, oldValue + 1);
            log.info("CLOCK AFTER INCEREMNT: {}", processData.getClock());
        }
    }

    public void synchronizeClock(ProcessData processData, List<Integer> messageClock){
        synchronized (processData){
            List<Integer> localClock = processData.getClock();
            if (messageClock.size() > localClock.size()) {
                int missingProcesses = messageClock.size() - localClock.size();
                localClock.addAll(Collections.nCopies(missingProcesses, 0));
            }
            for (int i = 0; i < messageClock.size(); i++) {
                localClock.set(i, Math.max(localClock.get(i), messageClock.get(i)));
            }
            log.info("CLOCK SYNCHRONIZATION: {}", processData.getClock());
        }
    }
}
