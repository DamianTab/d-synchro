package damian.tab.core.thread.model;

import damian.tab.core.monitor.algorithm.model.LockRequest;
import damian.tab.core.monitor.algorithm.model.NotifyRequest;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public class ProcessData {
    private final int processId;
    private final AtomicInteger notifyIdGenerator;
    // These need to be synchronized manually (in range of clock variable)
    private final List<Integer> clock = new ArrayList<>(List.of(0));
    private final List<LockRequest> lockUnlockRequests = new ArrayList<>();
    private final List<NotifyRequest> waitNotifyRequests = new ArrayList<>();


    //        todo Delete limitation - up to 214 separate threads and 10_000_000 notify messages by one thread in library
    public ProcessData(int processId) {
        this.processId = processId;
        notifyIdGenerator = new AtomicInteger(processId * 10_000_000);
    }

    public List<Integer> GetThreadSafeClock() {
        synchronized (this) {
            return clock;
        }
    }
}
