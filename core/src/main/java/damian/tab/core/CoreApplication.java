package damian.tab.core;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.task.DistributedTask;
import damian.tab.core.thread.ThreadConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class CoreApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);

        DistributedTask task = new DistributedTask() {
            @Override
            public void run() {
                DistributedMonitor monitor = this.createNewMonitor("LOL");
                DistributedMonitor monitor1 = this.createNewMonitor("LOL11");
                log.info(String.valueOf(monitor1));
            }
        };


        ThreadConfiguration factory = context.getBean(ThreadConfiguration.class);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(factory.createDistributedThread(task));
        executor.execute(factory.createDistributedThread(task));
        executor.shutdown();
    }

}
