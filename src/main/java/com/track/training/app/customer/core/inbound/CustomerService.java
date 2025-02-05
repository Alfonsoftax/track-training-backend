package com.track.training.app.customer.core.inbound;

import com.track.training.app.customer.core.domain.Customer;
import com.track.training.app.customer.core.inbound.dtos.AtletaDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/** Inbound Service Port for managing [Customer]. */
public interface CustomerService {

    /** With Events: [CustomerEvent]. */
    public Customer createCustomer(Customer input);

    /** */
    public Optional<Customer> getCustomer(Long id);

    /** With Events: [CustomerEvent]. */
    public Optional<Customer> updateCustomer(Long id, Customer input);

    /** With Events: [CustomerEvent]. */
    public void deleteCustomer(Long id);

    /** */
    public Page<Customer> searchCustomers(AtletaDto input, Pageable pageable);

}
