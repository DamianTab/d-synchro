package damian.tab.portmapper;

import damian.tab.core.CoreApplication;
import damian.tab.core.thread.PortMapperListenerRunnable;
import damian.tab.core.thread.ThreadConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class PortMapperApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
        ThreadConfiguration factory = context.getBean(ThreadConfiguration.class);
        PortMapperListenerRunnable portMapperListenerRunnable = factory.createPortMapperListenerRunnable();
        Executors.newSingleThreadExecutor().execute(portMapperListenerRunnable);
    }

}
