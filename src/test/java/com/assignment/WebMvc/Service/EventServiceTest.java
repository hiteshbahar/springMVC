package com.assignment.WebMvc.Service;

import com.assignment.WebMvc.dao.EventDao;
import com.assignment.WebMvc.model.Event;
import com.assignment.WebMvc.model.EventImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    EventService service;
    @Mock
    private EventDao eventDao;

    private static Event event;

    @BeforeAll
    static void setup(){
        event = new EventImpl(1L, "Marron5", Date.from(Instant.now()));
    }

    @Test
    void getEventById() {
        long eventId = 1L;
        when(eventDao.get(eventId)).thenReturn(Optional.of(event));
        assertEquals(event.getTitle(), service.getEventById(eventId).getTitle());

    }

    @Test
    void getEventsByTitle() {
        String title = "Marron5";
        when(eventDao.findByTitle(title)).thenReturn( Arrays.asList(event));
        assertEquals(event.getTitle(), service.getEventsByTitle(title, 1, 0)
                .stream()
                .findFirst()
                .map(Event::getTitle)
                .orElse("Can't find Title"));
    }


    @Test
    void createEvent() {
        when(eventDao.save(event)).thenReturn(event);
        assertEquals(event, service.createEvent(event));
    }

    @Test
    void updateEvent() {
        event.setTitle("someEvent");
        when(eventDao.update(event)).thenReturn(event);
        assertEquals(event.getTitle(), service.updateEvent(event).getTitle());
    }

    @Test
    void deleteEvent() {
        when(eventDao.delete(event.getId())).thenReturn(true);
        assertEquals(true, service.deleteEvent( 1L));
    }
}