package damian.tab.core.monitor.algorithm;

import damian.tab.core.proto.SynchroMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClockComparator {

    public boolean isClockLess(int processId, List<Integer> clock, SynchroMessage message) {
        int result = compareClocksAndProcessId(processId, clock, message);
        return result == -1 || (result == 0 && processId < message.getProcessID());
    }

    /**
     * @return -1 when clock < messageClock, 0  when clock == messageClock, 1 when clock > messageClock</>
     */
    public int compareClocksAndProcessId(int processId, List<Integer> clock, SynchroMessage message) {
        int result = compareClocks(clock, message.getClockList());
        if (result == -2) {
            result = processId < message.getProcessID() ? -1 : 1;
        }
        return result;
    }

    /**
     * @return -2 when ,cannot comparison, -1 when clock < messageClock, 0  when clock == messageClock, 1 when clock > messageClock</>
     */
    public int compareClocks(List<Integer> clock, List<Integer> messageClock) {
        if (clock.equals(messageClock)) {
            return 0;
        }
        boolean isGreater = true, isLess = true;
        for (int i = 0; i < messageClock.size(); i++) {
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
