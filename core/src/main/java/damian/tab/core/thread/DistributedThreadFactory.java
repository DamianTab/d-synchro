package damian.tab.core.thread;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.zeromq.ZContext;

@Configuration
@RequiredArgsConstructor
public class DistributedThreadFactory {

    private final ZContext zContext;

    @Bean
    @Scope(value = "prototype")
    public DistributedThread getObject(Runnable runnable) {
        return new DistributedThread(runnable, zContext);
    }
}
