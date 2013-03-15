package eu.execom.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.subethamail.wiser.Wiser;

/**
 * TODO add comments.
 * 
 * @author Dusko Vesin
 * 
 */
@Configuration
@PropertySource("classpath:/app_test.properties")
public class TestConfiguration {

    @Bean
    // necessary to be able to read from @PropertySource
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean(initMethod = "start")
    public Wiser wiser(@Value("${mail.smtp.host}") final String hostname, @Value("${mail.smtp.port}") final String port) {
        Wiser wiser = new Wiser();
        wiser.setHostname(hostname);
        wiser.setPort(Integer.valueOf(port));
        return wiser;
    }

}
