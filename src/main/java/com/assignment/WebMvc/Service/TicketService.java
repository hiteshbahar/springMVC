package com.assignment.WebMvc.Service;

import com.assignment.WebMvc.dao.TicketDao;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.Ticket;
import com.assignment.WebMvc.model.TicketImpl;
import com.assignment.WebMvc.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class TicketService {

    private TicketDao ticketDao;
    private TicketImpl ticket;

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    @Autowired
    public void setTicketDao(TicketDao ticketDao) {
        this.ticketDao = ticketDao;
    }

    /*public void setTicket(TicketImpl ticket) {
        this.ticket = ticket;
    }*/

    /**
     * Book ticket for a specified event on behalf of specified user.
     *
     * @param userId   User Id.
     * @param eventId  Event Id.
     * @param place    Place number.
     * @param category Service category.
     * @return Booked ticket object.
     * @throws IllegalStateException if this place has already been booked.
     */
    public Ticket bookTicket(long userId, long eventId, int place, Ticket.Category category) {
        logger.debug("Booking Ticket");
        // Find user and ticket
        ticket = new TicketImpl();
        ticket.setId(new Random().nextInt(100));
        ticket.setUserId(userId);
        ticket.setEventId(eventId);
        ticket.setPlace(place);
        ticket.setCategory(category);
        return ticketDao.save(ticket);
    }

    /**
     * Get all booked tickets for specified user. Tickets should be sorted by event date in descending order.
     *
     * @param user     User
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(User user, int pageSize, int pageNum) {
        logger.debug("Getting Booked tickets for {}", user.getId());
        ticket = new TicketImpl();
        ticket.setUserId(user.getId());
        return ticketDao.getAll(ticket)
                .stream()
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Get all booked tickets for specified event. Tickets should be sorted in by user email in ascending order.
     *
     * @param event    Event
     * @param pageSize Pagination param. Number of tickets to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of Ticket objects.
     */
    public List<Ticket> getBookedTickets(Event event, int pageSize, int pageNum) {
        logger.debug("Getting Tickets details for Event: {}", event.getId());
        ticket = new TicketImpl();
        ticket.setEventId(event.getId());
        return ticketDao.getAll(ticket)
                .stream()
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Cancel ticket with a specified id.
     *
     * @param ticketId Ticket id.
     * @return Flag whether anything has been canceled.
     */
    public boolean cancelTicket(long ticketId) {
        logger.debug("Cancelling ticket");
        return ticketDao.delete(ticketId);
    }
}
