package com.izertis.architecture.examples.customer.infrastructure.jpa.inmemory;

import com.izertis.architecture.examples.customer.core.domain.Customer;
import com.izertis.architecture.examples.customer.core.outbound.jpa.CustomerRepository;

public class CustomerRepositoryInMemory extends InMemoryJpaRepository<Customer> implements CustomerRepository {

}
