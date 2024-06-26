package com.wbajjouk.taskmanager;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Configuration
@EnableJpaRepositories(basePackages = "com.wbajjouk.taskmanager") //how can I point to repositories in case of packagging by features
@EnableScheduling
public class AppConfig {

    @Bean
    public Foo internalFoo(
            // dependencies
    ) {
        return new Foo();
    }

    @Bean
    public Foo externalFoo(
            // dependencies
    ) {
        return new Foo();
    }

    public interface MailSender {}
    public static class MockSender implements MailSender {}
    public static class AwsSNSSender implements MailSender {}

    @Bean
    public MailSender sender(
            // dependencies
    ) {
        if (/* condition based on configuration) */ true) {
            return new MockSender();
        } else {
            return new AwsSNSSender();
        }
    }

    @Service
    public static class Foo {

    }
}


//notes :@Configuration indicates that its primary purpose is as a source of bean definitions.

