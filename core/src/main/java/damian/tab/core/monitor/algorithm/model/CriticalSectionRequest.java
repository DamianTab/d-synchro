package damian.tab.core.monitor.algorithm.model;

import lombok.Data;

import java.util.List;

@Data
public class CriticalSectionRequest {
    private final List<Integer> ackList;
    private final List<Integer> waitingQueue;
}
