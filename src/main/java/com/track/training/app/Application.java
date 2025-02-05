package com.track.training.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.track.training.app.interfaces.CustomJpaRepositoryFactoryBean;

@SpringBootApplication
@ComponentScan(basePackages = "com.track.training.app.atleta")
@EnableJpaRepositories(
	    basePackages = "com.track.training.app",
	    repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class
	)public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
