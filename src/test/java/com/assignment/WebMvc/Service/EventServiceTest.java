package com.assignment.WebMvc.Service;

import com.assignment.WebMvc.model.EventImpl;
import com.assignment.WebMvc.repositories.EventImplRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks
    EventService service;
    @Mock
    private EventImplRepository eventImplRepository;

    private static EventImpl event;

    @BeforeAll
    static void setup(){
        event = new EventImpl(1L, "Marron5", Date.from(Instant.now()));
    }

    @Test
    void getEventById() {
        long eventId = 1L;
        when(eventImplRepository.findById(eventId)).thenReturn(Optional.of(event));
        assertEquals(event.getTitle(), service.getEventById(eventId).getTitle());

    }


    @Test
    void createEvent() {
       when(eventImplRepository.save(any())).thenReturn(event);
        assertEquals(event, service.createEvent(event));
    }

    @Test
    void updateEvent() {
        event.setTitle("someEvent");
        when(eventImplRepository.findById(anyLong())).thenReturn(Optional.ofNullable(event));
        when(eventImplRepository.save(any())).thenReturn(event);
        assertEquals(event.getTitle(), service.updateEvent(event).getTitle());
    }

    @Test
    void deleteEvent() {
        when(eventImplRepository.findById(anyLong())).thenReturn(Optional.ofNullable(event));
        doNothing().when(eventImplRepository).delete(any());
        assertEquals(true, service.deleteEvent( 1L));
    }
}