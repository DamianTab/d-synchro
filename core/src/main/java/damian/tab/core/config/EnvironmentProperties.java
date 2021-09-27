package damian.tab.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "config")
public class EnvironmentProperties {
    private String publisherAddress;
    private String portMapperAddress;
}
