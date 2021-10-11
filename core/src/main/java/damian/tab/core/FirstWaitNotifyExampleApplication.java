package damian.tab.core;

import damian.tab.core.monitor.DistributedMonitor;
import damian.tab.core.task.DistributedTask;
import damian.tab.core.thread.ThreadConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class FirstWaitNotifyExampleApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(FirstWaitNotifyExampleApplication.class, args);

        DistributedTask task = new DistributedTask() {
            @SneakyThrows
            @Override
            public void run() {
                int time = 4_000;
                DistributedMonitor monitor = this.createNewMonitor("DistributedMonitor");

                monitor.dWait();
                Thread.sleep(time);

                monitor.dNotify();
                log.info("FINISH ----------------------");
            }
        };


        ThreadConfiguration factory = context.getBean(ThreadConfiguration.class);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.execute(factory.createDistributedThread(task));
        executor.shutdown();
    }

}
