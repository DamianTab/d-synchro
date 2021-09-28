package damian.tab.core;

import damian.tab.core.thread.DistributedThread;
import damian.tab.core.thread.ThreadConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@Slf4j
@SpringBootApplication
public class CoreApplication {

//    todo zewnetrzny thread do odpalania zadania z inicjalizacją ZCOntext oraz osobnego threada na odbieranie wiadomości - wtedy kazdy nowy monitor ma dokładnie tego samego ListenThreada
//    todo jesli beda dochodzi nowe sockety to tylko i wylacznie w ListenThreadzie - thread safe

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);

//        todo 2
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(new DistributedThread());

//        todo 3
        Runnable runnable = () -> {};
        Runnable runnable1 = () -> {};

        ThreadConfiguration factory = context.getBean(ThreadConfiguration.class);
        DistributedThread thread = factory.createDistributedThread(runnable);
        DistributedThread thread1 = factory.createDistributedThread(runnable1);
        thread.start();
        thread1.start();

        try {
            thread.join();
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
