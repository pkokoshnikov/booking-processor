package com.pkokoshnikov.bookingservice.model.request;

import java.io.Serializable;
import java.util.Arrays;

/**
 * User: pako1113
 * Date: 09.07.15
 * This class provides POJO model for input model of booking batch
 */
public class BookingBatch implements Serializable{
    private String startOfWorkDay;
    private String endOfWorkDay;
    private BookingItem[] bookingItems;

    public BookingBatch(String startOfWorkDay, String endOfWorkDay, BookingItem[] bookingItems) {
        this.startOfWorkDay = startOfWorkDay;
        this.endOfWorkDay = endOfWorkDay;
        this.bookingItems = bookingItems;
    }

    public BookingBatch() {
    }

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

    @Override
    public String toString() {
        return "BookingBatch{" +
                "startOfWorkDay='" + startOfWorkDay + '\'' +
                ", endOfWorkDay='" + endOfWorkDay + '\'' +
                ", bookingItems=" + Arrays.toString(bookingItems) +
                '}';
    }
}
