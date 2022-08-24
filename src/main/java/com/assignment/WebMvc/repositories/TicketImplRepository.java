package com.assignment.WebMvc.repositories;

import com.assignment.WebMvc.model.TicketImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketImplRepository extends JpaRepository<TicketImpl, Long> {
    Optional<TicketImpl> findById(Long id);

    List<TicketImpl> findAllById(long userId);
}
