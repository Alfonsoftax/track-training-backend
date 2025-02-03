package com.izertis.architecture.examples.customer.infrastructure.jpa.inmemory;

import com.track.training.app.customer.core.domain.Customer;
import com.track.training.app.customer.core.outbound.jpa.CustomerRepository;

public class CustomerRepositoryInMemory extends InMemoryJpaRepository<Customer> implements CustomerRepository {

}
