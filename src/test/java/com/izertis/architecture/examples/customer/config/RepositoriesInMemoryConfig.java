package com.izertis.architecture.examples.customer.config;

import com.izertis.architecture.examples.customer.infrastructure.jpa.inmemory.CustomerRepositoryInMemory;
import com.track.training.app.customer.core.outbound.jpa.CustomerRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

// @Configuration
public class RepositoriesInMemoryConfig {

    protected final CustomerRepository customerRepository = new CustomerRepositoryInMemory();

    @Bean
    @Primary
    public <T extends CustomerRepository> T customerRepository() {
        return (T) customerRepository;
    }

}
