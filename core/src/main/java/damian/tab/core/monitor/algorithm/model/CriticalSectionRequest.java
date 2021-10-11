package damian.tab.core.monitor.algorithm.model;

import damian.tab.core.proto.SynchroMessage;
import damian.tab.core.thread.model.ProcessData;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;


@Getter
public abstract class CriticalSectionRequest {
    protected final String monitorId;
    protected final List<Integer> clockTimestamp;
    protected final List<Integer> ackList;
    protected final List<SynchroMessage> waitingQueue;

    protected CriticalSectionRequest(String monitorId, ProcessData processData) {
        this.monitorId = monitorId;
        this.clockTimestamp = new ArrayList<>(processData.getClock());
        this.ackList = new ArrayList<>(Collections.nCopies(processData.getClock().size(), 0));
        this.waitingQueue = new ArrayList<>();
        this.ackList.set(processData.getProcessId(), 1);
    }

    public boolean isPossibleToAcquireCriticalSection(){
        HashSet<Integer> ackSet = new HashSet<>(ackList);
        return ackSet.size() == 1 && ackSet.contains(1);
    }
}
