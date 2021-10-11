package damian.tab.core.monitor.algorithm.model;

import damian.tab.core.thread.model.ProcessData;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;


@Getter
@EqualsAndHashCode(callSuper = true)
public class NotifyRequest extends LockRequest {
    private final List<Integer> receivedNotifies;

    public NotifyRequest(String monitorId, ProcessData processData) {
        super(monitorId, processData);
        this.receivedNotifies = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "NotifyRequest{" +
                "receivedNotifies=" + receivedNotifies +
                "}-" + super.toString();
    }
}
