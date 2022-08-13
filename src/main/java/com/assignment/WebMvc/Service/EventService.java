package com.assignment.WebMvc.Service;


import com.assignment.WebMvc.dao.EventDao;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.EventImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private Logger logger = LoggerFactory.getLogger(EventService.class);

    private EventDao eventDao;

    @Autowired
    public void setEventDao(EventDao eventDao) {
        this.eventDao = eventDao;
    }

    /**
     * Gets event by its id.
     *
     * @param eventId
     * @return Event.
     */
    public Event getEventById(long eventId) {
        logger.debug("Inside method getting event by id");
        return eventDao.get(eventId)
                .orElse(new EventImpl());
    }

    /**
     * Get list of events by matching title. Title is matched using 'contains' approach.
     * In case nothing was found, empty list is returned.
     *
     * @param title    Event title or it's part.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsByTitle(String title, int pageSize, int pageNum) {
        logger.debug("Inside method getting event by title");
        return eventDao.findByTitle(title)
                .stream()
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Get list of events for specified day.
     * In case nothing was found, empty list is returned.
     *
     * @param day      Date object from which day information is extracted.
     * @param pageSize Pagination param. Number of events to return on a page.
     * @param pageNum  Pagination param. Number of the page to return. Starts from 1.
     * @return List of events.
     */
    public List<Event> getEventsForDay(Date day, int pageSize, int pageNum) {
        logger.debug("Inside method getting event by Day");
        return eventDao.findByDate(day)
                .stream()
                .limit(pageSize)
                .collect(Collectors.toList());
    }

    /**
     * Creates new event. Event id should be auto-generated.
     *
     * @param event Event data.
     * @return Created Event object.
     */
    public Event createEvent(Event event) {
        logger.debug("Inside method creating event");
        return eventDao.save(event);
    }

    /**
     * Updates event using given data.
     *
     * @param event Event data for update. Should have id set.
     * @return Updated Event object.
     */
    public Event updateEvent(Event event) {
        logger.debug("Inside method updating event");
        return eventDao.update(event);
    }

    /**
     * Deletes event by its id.
     *
     * @param eventId Event id.
     * @return Flag that shows whether event has been deleted.
     */
    public boolean deleteEvent(long eventId) {
        logger.debug("Inside method deleting event by id");
        return eventDao.delete(eventId);
    }
}