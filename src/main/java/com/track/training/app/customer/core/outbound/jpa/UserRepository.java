package com.track.training.app.customer.core.outbound.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.track.training.app.customer.core.domain.Customer;
import com.track.training.app.customer.core.domain.Usuario;

/** Spring Data JPA repository for the Customer entity. */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<Usuario,Long> {
    Optional<Usuario> findByUsername(String username); 
}
