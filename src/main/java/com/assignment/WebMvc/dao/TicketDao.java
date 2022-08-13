package com.assignment.WebMvc.dao;



import com.assignment.WebMvc.model.Ticket;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class TicketDao implements Dao<Ticket> {
    public static final String TICKET = "TICKET:";
    public static Map<String, Ticket> ticketMap = new HashMap<>();

    @Override
    public Optional<Ticket> get(long id) {
        return Optional.ofNullable(ticketMap.get(TICKET + id));
    }

    @Override
    public List<Ticket> getAll(Ticket ticket) {
        List<Ticket> ticketsList = ticketMap.values().stream().collect(Collectors.toList());;
        return ticketsList.stream()
                .filter(Objects::nonNull)
                .filter(t -> Objects.equals(t.getUserId(), ticket.getUserId()))
                .collect(Collectors.toList());

    }

    @Override
    public Ticket save(Ticket ticket) {

        ticketMap.put( TICKET+ ticket.getId(), ticket);
        return get(ticket.getId()).orElse(null);
    }

    @Override
    public Ticket update(Ticket ticket) {
        return null;
    }

    @Override
    public boolean delete(long id) {
        return false;
    }
}
