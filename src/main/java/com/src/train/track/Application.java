package com.src.train.track;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.src.train.track.general.helper.CustomJpaRepositoryFactoryBean;

@ComponentScan(basePackages = "com.src.train.track")
@EnableJpaRepositories(
	    basePackages = "com.src.train.track",
	    repositoryFactoryBeanClass = CustomJpaRepositoryFactoryBean.class
	)
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
