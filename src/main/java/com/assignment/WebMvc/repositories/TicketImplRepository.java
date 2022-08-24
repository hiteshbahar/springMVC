package com.assignment.WebMvc.repositories;

import com.assignment.WebMvc.model.TicketImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface TicketImplRepository extends JpaRepository<TicketImpl, Long> {
    Optional<TicketImpl> findById(Long id);


    @Query(value = "\n" +
            "select ticket.ticket_id, ticket.category, ticket.\"eventId\", ticket.\"userId\", ticket.place\n" +
            "\tfrom ticket\n" +
            "\tleft join public.\"event\" on event.event_id = ticket.\"eventId\"\n" +
            "\twhere ticket.\"userId\" = ?1",
    nativeQuery = true)
    List<TicketImpl> findByUserId(long id);

    @Query(value = "select ticket.ticket_id, ticket.category, ticket.\"eventId\", ticket.\"userId\", ticket.place\n" +
            "\tfrom ticket\n" +
            "\tleft join public.\"user\" on \"user\".user_id = ticket.\"userId\"\n" +
            "\twhere ticket.\"eventId\" = ?1",
    nativeQuery = true)
    List<TicketImpl> findByEventId(long id);
}
