package com.track.training.app.customer.core.outbound.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.track.training.app.customer.core.domain.Customer;

/** Spring Data JPA repository for the Customer entity. */
@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
