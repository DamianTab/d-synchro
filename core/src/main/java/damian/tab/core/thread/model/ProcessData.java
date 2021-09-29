package damian.tab.core.thread.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Data
public class ProcessData {
    private final int processId;
    private final List<Integer> clock = new CopyOnWriteArrayList<>(new Integer[]{0});
}
