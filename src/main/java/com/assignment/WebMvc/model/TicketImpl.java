package com.assignment.WebMvc.model;

public class TicketImpl implements Ticket {
    private long id;
    private int place;
    private long eventId;
    private long userId;
    private Category category;

    public TicketImpl() {}

    public TicketImpl(long id, int place, long eventId, long userId, Category category) {
        this.id = id;
        this.place = place;
        this.eventId = eventId;
        this.userId = userId;
        this.category = category;
    }

    /**
     * Ticket Id. UNIQUE.
     *
     * @return Ticket Id.
     */


    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getEventId() {
        return eventId;
    }

    @Override
    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    @Override
    public long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Override
    public Category getCategory() {
        return category;
    }

    @Override
    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public int getPlace() {
        return place;
    }

    @Override
    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "TicketImpl{" +
                "id=" + id +
                ", place=" + place +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", category=" + category +
                '}';
    }
}
