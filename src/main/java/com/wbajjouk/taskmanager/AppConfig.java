package com.wbajjouk.taskmanager;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.wbajjouk.taskmanager") //how can I point to repositories in case of packagging by features

public class AppConfig {

}


//notes :@Configuration indicates that its primary purpose is as a source of bean definitions.

