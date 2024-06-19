package com.wbajjouk.taskmanager;

import com.wbajjouk.taskmanager.taskmanagement.TaskRepository;
import com.wbajjouk.taskmanager.taskmanagement.TaskService;
import com.wbajjouk.taskmanager.usermanagement.UserRepository;
import com.wbajjouk.taskmanager.usermanagement.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.wbajjouk.taskmanager") //how can I point to repositories in case of packagging by features

public class AppConfig {

    // Define beans for services
    @Bean
    public UserService userService(UserRepository userRepository) {
        return new UserService(userRepository);
    }

    @Bean
    public TaskService taskService(TaskRepository taskRepository) {
        return new TaskService(taskRepository);
    }

}


//notes :@Configuration indicates that its primary purpose is as a source of bean definitions.

