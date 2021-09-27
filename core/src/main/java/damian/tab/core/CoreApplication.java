package damian.tab.core;

import damian.tab.core.config.EnvironmentProperties;
import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.thread.DistributedThread;
import damian.tab.core.thread.PortMapperListenerRunnable;
import damian.tab.core.thread.ThreadConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.zeromq.ZContext;

@Slf4j
@SpringBootApplication
public class CoreApplication {

//    todo zewnetrzny thread do odpalania zadania z inicjalizacją ZCOntext oraz osobnego threada na odbieranie wiadomości - wtedy kazdy nowy monitor ma dokładnie tego samego ListenThreada
//    todo jesli beda dochodzi nowe sockety to tylko i wylacznie w ListenThreadzie - thread safe
//    todo jednorazowo przejsc po kolei zainicjalizowac komunikacje z portmapperem itp. - taka podstawowa sciezka - a dopiero potem reszta

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
        EnvironmentProperties properties = context.getBean(EnvironmentProperties.class);
        ZContext zContext = context.getBean(ZContext.class);

        InitRequestMessage message = InitRequestMessage.newBuilder()
                .setAddress("lol")
                .setReady(true)
                .build();

//        REQ
//        SocketProxy proxy = SocketProxy.builder()
//                .context(zContext)
//                .address(properties.getAddress())
//                .type(SocketType.REQ)
//                .build();
//
//        while (!Thread.currentThread().isInterrupted()) {
//            log.info("Sending request");
//            proxy.send("WIADOMOSC");
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            log.info("Received: [ {} ]", proxy.receive());
//        }

//        SUB
//        SocketProxy proxy = SocketProxy.builder()
//                .context(zContext)
//                .address(properties.getAddress())
//                .type(SocketType.SUB)
//                .build();
//
//        while (!Thread.currentThread().isInterrupted()) {
//            log.info("Received: [ {} ]", proxy.receive());
//        }


//        todo 2
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(new DistributedThread());

//        todo 3
        Runnable runnable = () -> System.out.println("lol");
        Runnable runnable1 = () -> System.out.println("wtf");

        ThreadConfiguration factory = context.getBean(ThreadConfiguration.class);
        DistributedThread thread = factory.createDistributedThread(runnable);
        DistributedThread thread1 = factory.createDistributedThread(runnable1);
        thread.start();
        thread1.start();
//        PortMapperListenerRunnable portMapperListenerRunnable = factory.createPortMapperListenerRunnable();
//        new Thread(portMapperListenerRunnable).start();

    }

}
