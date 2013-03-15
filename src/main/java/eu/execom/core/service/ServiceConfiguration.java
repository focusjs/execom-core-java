package eu.execom.core.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Core spring configuration of the system.
 * 
 * @author Dusko Vesin
 * 
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan(basePackages = {"eu.execom.core.service"})
public class ServiceConfiguration {

    private static final int PASSWORD_STRENGTH = 256;

    /**
     * Password encoder.
     * 
     * @return {@link ShaPasswordEncoder} with 265 strength
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new ShaPasswordEncoder(PASSWORD_STRENGTH);
    }
}
