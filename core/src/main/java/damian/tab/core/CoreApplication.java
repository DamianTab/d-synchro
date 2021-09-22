package damian.tab.core;

import damian.tab.core.proto.InitRequestMessage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
		InitRequestMessage lol = InitRequestMessage.newBuilder()
				.setAddress("lol")
				.setReady(true)
				.build();
		System.out.println(lol);
	}

}
