package com.assignment.WebMvc.repositories;

import com.assignment.WebMvc.model.UserImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserImplRepository extends JpaRepository<UserImpl, Long> {
    Optional<UserImpl> findById(long id);

    Optional<UserImpl> findByEmailIgnoreCase(String email);

    List<UserImpl> findByNameIgnoreCase(String name);
}
