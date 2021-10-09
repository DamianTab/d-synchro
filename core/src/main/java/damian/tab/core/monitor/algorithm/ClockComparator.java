package damian.tab.core.monitor.algorithm;

import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.model.ProcessData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClockComparator {

    public boolean isInternalClockLess(ProcessData processData, SynchroMessage message){
        int result = compareClocksAndProcessId(processData, message);
        return result == -1 || (result == 0 && processData.getProcessId() < message.getProcessID());
    }

    /**
     * @return -1 when clock < messageClock, 0  when clock == messageClock, 1 when clock > messageClock</>
     */
    public int compareClocksAndProcessId(ProcessData processData, SynchroMessage message) {
        int result = compareClocks(processData, message.getClockList());
        if (result == -2) {
            result = processData.getProcessId() < message.getProcessID() ? -1 : 1;
        }
        return result;
    }

    /**
     * @return -2 when ,cannot comparison, -1 when clock < messageClock, 0  when clock == messageClock, 1 when clock > messageClock</>
     */
    public int compareClocks(ProcessData processData, List<Integer> messageClock) {
        synchronized (processData) {
            List<Integer> clock = processData.getClock();
            if (clock.equals(messageClock)) {
                return 0;
            }
            boolean isGreater = true, isLess = true;
            for (int i = 0; i < clock.size(); i++) {
                if (clock.get(i) < messageClock.get(i)) {
                    isGreater = false;
                } else if (clock.get(i) > messageClock.get(i)) {
                    isLess = false;
                }
            }
            if (!isGreater && !isLess) {
                return -2;
            }
            return isLess ? -1 : 1;
        }
    }
}
