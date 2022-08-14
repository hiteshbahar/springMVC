package com.assignment.WebMvc.dao;



import com.assignment.WebMvc.model.Event;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EventDao implements Dao<Event> {
    public static final String EVENT = "Event:";
    public static Map<String, Event> eventMap = new HashMap<>();

    @Override
    public Optional<Event> get(long id) {
        return Optional.ofNullable(eventMap.get(EVENT + id));
    }

    @Override
    public List<Event> getAll(Event event) {
        return new ArrayList<>(eventMap.values())
                .stream()
                .filter(Objects::nonNull)
                .filter(event1 -> Objects.equals(event1.getId(), event.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public Event save(Event event) {
        eventMap.put(EVENT+event.getId(), event);
        return eventMap.get(EVENT+event.getId());
    }

    @Override
    public Event update(Event event) {
        if(eventMap.containsKey(EVENT+event.getId())) {
            eventMap.replace(EVENT + event.getId(), event);
            return eventMap.get(EVENT+event.getId());
        }
        return null;
    }

    @Override
    public boolean delete(long id) {
        if(eventMap.containsKey(EVENT+id)) {
            eventMap.remove(EVENT + id);
            return true;
        }
        return false;
    }

    public List<Event> findByDate(Date day) {
        return new ArrayList<>(eventMap.values())
                .stream()
                .filter(Objects::nonNull)
                .filter(event -> event.getDate().getDay() == day.getDay())
                .collect(Collectors.toList());
    }

    public List<Event> findByTitle(String title) {
        return new ArrayList<>(eventMap.values())
                .stream()
                .filter(Objects::nonNull)
                .filter(event -> event.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
