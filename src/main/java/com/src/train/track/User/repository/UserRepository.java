package com.src.train.track.User.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.train.track.User.model.Usuario;
import com.src.train.track.interfaz.CustomQueryDslPredicateExecutor;

@Repository
public interface UserRepository
        extends JpaRepository<Usuario, Long>, CustomQueryDslPredicateExecutor<Usuario> {
    Optional<Usuario> findByUsername(String username); 

}
