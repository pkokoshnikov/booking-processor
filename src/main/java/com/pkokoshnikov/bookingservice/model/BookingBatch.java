package com.pkokoshnikov.bookingservice.model;

import java.io.Serializable;

/**
 * User: pako1113
 * Date: 09.07.15
 */
public class BookingBatch implements Serializable{
    private String startOfWorkDay;
    private String endOfWorkDay;
    private BookingItem[] bookingItems;

    public String getStartOfWorkDay() {
        return startOfWorkDay;
    }

    public void setStartOfWorkDay(String startOfWorkDay) {
        this.startOfWorkDay = startOfWorkDay;
    }

    public String getEndOfWorkDay() {
        return endOfWorkDay;
    }

    public void setEndOfWorkDay(String endOfWorkDay) {
        this.endOfWorkDay = endOfWorkDay;
    }

    public BookingItem[] getBookingItems() {
        return bookingItems;
    }

    public void setBookingItems(BookingItem[] bookingItems) {
        this.bookingItems = bookingItems;
    }
}
