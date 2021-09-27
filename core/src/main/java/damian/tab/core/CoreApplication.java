package damian.tab.core;

import damian.tab.core.config.EnvironmentProperties;
import damian.tab.core.proto.InitRequestMessage;
import damian.tab.core.thread.DistributedThread;
import damian.tab.core.thread.DistributedThreadFactory;
import damian.tab.core.zmq.SocketProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@SpringBootApplication
public class CoreApplication {

//    todo zewnetrzny thread do odpalania zadania z inicjalizacją ZCOntext oraz osobnego threada na odbieranie wiadomości - wtedy kazdy nowy monitor ma dokładnie tego samego ListenThreada
//    todo jesli beda dochodzi nowe sockety to tylko i wylacznie w ListenThreadzie - thread safe
//    todo jednorazowo przejsc po kolei zainicjalizowac komunikacje z portmapperem itp. - taka podstawowa sciezka - a dopiero potem reszta

    public static void main(String[] args) {
//        todo 1

        ConfigurableApplicationContext context = SpringApplication.run(CoreApplication.class, args);
        InitRequestMessage lol = InitRequestMessage.newBuilder()
                .setAddress("lol")
                .setReady(true)
                .build();
        System.out.println(lol);

        EnvironmentProperties properties = context.getBean(EnvironmentProperties.class);
        log.info(properties.getAddress());
        log.info(properties.getPort());


        ZContext zContext = context.getBean(ZContext.class);


//        REQ
//        SocketProxy proxy = SocketProxy.builder()
//                .context(zContext)
//                .address(properties.getAddress())
//                .port(properties.getPort())
//                .type(SocketType.REQ)
//                .build();
//
//        while (!Thread.currentThread().isInterrupted()) {
//            System.out.println("Sending request ");
//            proxy.getSocket().send("WIADOMOSC".getBytes(ZMQ.CHARSET), 0);
//            byte[] reply = proxy.getSocket().recv(0);
//            System.out.println("Received: [" + new String(reply, ZMQ.CHARSET) + "]");
//        }

//        SUB
        SocketProxy proxy = SocketProxy.builder()
                .context(zContext)
                .address(properties.getAddress())
                .port(properties.getPort())
                .type(SocketType.SUB)
                .build();

        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Received: [" + proxy.getSocket().recvStr() + "]");
        }



//        todo 2
//        ExecutorService executor = Executors.newSingleThreadExecutor();
//        executor.execute(new DistributedThread());

//        todo 3
//        Runnable runnable = () -> System.out.println("lol");
//        Runnable runnable1 = () -> System.out.println("wtf");
//
//        DistributedThreadFactory factory = context.getBean(DistributedThreadFactory.class);
//        DistributedThread thread = factory.getObject(runnable);
//        DistributedThread thread1 = factory.getObject(runnable1);
//        thread.start();
//        thread1.start();

    }

}
