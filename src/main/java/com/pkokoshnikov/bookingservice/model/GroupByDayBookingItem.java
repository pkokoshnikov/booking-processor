package com.pkokoshnikov.bookingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkokoshnikov.bookingservice.util.formatters.ResponseMetingDateSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: pako1113
 * Date: 13.07.15
 * This class provides model for group by day response
 */
public class GroupByDayBookingItem implements ResponseView, Serializable{
    private List<BookingItemResponse> bookingItems = new ArrayList<>();

    @JsonSerialize(using = ResponseMetingDateSerializer.class)
    private Date dateOfMeeting;

    public void addBookingItem(BookingItem bookingItem) {
        bookingItems.add(new BookingItemResponse(bookingItem));
    }

    public List<BookingItemResponse> getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(List<BookingItemResponse> bookingItems) {
        this.bookingItems = bookingItems;
    }

    public Date getDateOfMeeting() {
        return dateOfMeeting;
    }

    public void setDateOfMeeting(Date dateOfMeeting) {
        this.dateOfMeeting = dateOfMeeting;
    }

    @Override
    public String toString() {
        return "GroupByDayBookingItem{" +
                "bookingItems=" + bookingItems +
                ", dateOfMeeting=" + dateOfMeeting +
                '}';
    }

}
