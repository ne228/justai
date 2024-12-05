package test.task.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ApplicationProperties {


    private static String version;

    @Value("${vk.api.version}")
    public void setVersion(String version) {
        ApplicationProperties.version = version;
    }

    public static String getVersion() {
        return version;
    }
}
