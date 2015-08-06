package com.pkokoshnikov.bookingservice.util;

import com.pkokoshnikov.bookingservice.model.BookingItem;

import java.util.Date;

/**
 * User: pako1113
 * Date: 27.07.15
 */
public class BookingUtils {
    public static Date getMeetingEndTime(BookingItem bookingItem) {
        return new Date(bookingItem.getMeetingStartTime().getTime() + bookingItem.getDuration().intValue() * 60 * 60 * 1000);
    }
}
