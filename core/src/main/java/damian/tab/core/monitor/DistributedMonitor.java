package damian.tab.core.monitor;

import damian.tab.core.thread.ClientListenerRunnable;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DistributedMonitor {
    private final ClientListenerRunnable clientListenerRunnable;
    private final String objectId;
}
