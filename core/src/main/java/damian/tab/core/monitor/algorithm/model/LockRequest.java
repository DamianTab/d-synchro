package damian.tab.core.monitor.algorithm.model;

import damian.tab.core.thread.model.ProcessData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Getter
public class LockRequest {
    private final List<Integer> ackList;
    private final List<Integer> waitingQueue;

    public LockRequest(ProcessData processData) {
        this.ackList = new ArrayList<>(Collections.nCopies(processData.getClock().size(), 0));
        this.waitingQueue = new ArrayList<>();
        this.ackList.set(processData.getProcessId(), 1);
    }

    public boolean isPossibleToAcquireCriticalSection(){
        HashSet<Integer> ackSet = new HashSet<>(ackList);
        return ackSet.size() == 1 && ackSet.contains(1);
    }
}
