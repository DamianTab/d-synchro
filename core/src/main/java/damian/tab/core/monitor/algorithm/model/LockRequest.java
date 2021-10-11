package damian.tab.core.monitor.algorithm.model;

import damian.tab.core.thread.model.ProcessData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class LockRequest extends CriticalSectionRequest {

    @Setter
    private boolean inCriticalSection;

    public LockRequest(String monitorId, ProcessData processData) {
        super(monitorId, processData);
    }

    @Override
    public String toString() {
        return "LockRequest{" +
                "monitorId='" + monitorId + '\'' +
                ", clockTimestamp=" + clockTimestamp +
                ", ackList=" + ackList +
                ", waitingQueue=" + waitingQueue +
                ", inCriticalSection=" + inCriticalSection +
                '}';
    }
}
