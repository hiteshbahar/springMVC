package com.assignment.WebMvc.repositories;

import com.assignment.WebMvc.model.EventImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventImplRepository extends JpaRepository<EventImpl, Long> {
    Optional<EventImpl> findById(long id);
    List<EventImpl> findByTitleIgnoreCase(String name);
    List<EventImpl> findByDate(Date date);
}
