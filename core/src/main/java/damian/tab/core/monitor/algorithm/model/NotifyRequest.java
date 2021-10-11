package damian.tab.core.monitor.algorithm.model;

import damian.tab.core.thread.model.ProcessData;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.LinkedList;
import java.util.List;


@Getter
@EqualsAndHashCode(callSuper = true)
public class NotifyRequest extends CriticalSectionRequest {
    private final List<Integer> ongoingNotifySessions;

    public NotifyRequest(String monitorId, ProcessData processData) {
        super(monitorId, processData);
        this.ongoingNotifySessions = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "NotifyRequest{" +
                "monitorId='" + monitorId + '\'' +
                ", clockTimestamp=" + clockTimestamp +
                ", ackList=" + ackList +
                ", waitingQueue=" + waitingQueue +
                ", ongoingNotifySessions=" + ongoingNotifySessions +
                '}';
    }
}
