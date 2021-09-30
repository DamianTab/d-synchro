package damian.tab.core.thread.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Data
public class ProcessData {
    private final int processId;
    private final List<Integer> clock = new CopyOnWriteArrayList<>(new Integer[]{0});
    private final AtomicInteger notifyIdGenerator;


//        todo Delete limitation - up to 214 separate threads and 10_000_000 notify messages by one thread in library
    public ProcessData(int processId) {
        this.processId = processId;
        notifyIdGenerator = new AtomicInteger(processId * 10_000_000);
    }
}
