package com.izertis.architecture.examples.customer.core.outbound.jpa;

import com.izertis.architecture.examples.customer.core.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Spring Data JPA repository for the Customer entity. */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
