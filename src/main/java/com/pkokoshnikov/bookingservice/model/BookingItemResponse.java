package com.pkokoshnikov.bookingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkokoshnikov.bookingservice.util.formatters.MeetingDateSerializer;

import java.util.Calendar;
import java.util.Date;

/**
 * User: pako1113
 * Date: 13.07.15
 */
public class BookingItemResponse {
    private final Calendar cal = Calendar.getInstance();
    private BookingItem bookingItem;

    public BookingItemResponse(BookingItem bookingItem) {
        this.bookingItem = bookingItem;
    }

    public BookingItemResponse() {
        this.bookingItem = new BookingItem();
    }

    @JsonSerialize(using = MeetingDateSerializer.class)
    public Date getMeetingStartTime() {
        return bookingItem.getMeetingStartTime();
    }

    @JsonSerialize(using = MeetingDateSerializer.class)
    public Date getMeetingEndTime() {
        cal.setTime(bookingItem.getMeetingStartTime());
        cal.add(Calendar.HOUR_OF_DAY, bookingItem.getDuration());

        return cal.getTime();
    }

    public void setMeetingStartTime(String meetingStartTime) {

    }

    public void setMeetingEndTime(String meetingEndTime) {

    }

    public String getUserId() {
        return bookingItem.getUserId();
    }

    public void setUserId(String userId) {
        bookingItem.setUserId(userId);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingItemResponse that = (BookingItemResponse) o;

        if (bookingItem != null ? !bookingItem.equals(that.bookingItem) : that.bookingItem != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return bookingItem != null ? bookingItem.hashCode() : 0;
    }
}
