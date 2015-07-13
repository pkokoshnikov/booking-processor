package com.pkokoshnikov.bookingservice.util.comparators;

import com.pkokoshnikov.bookingservice.model.BookingItem;

import java.util.Comparator;

/**
 * Created by pavel on 11.07.2015.
 */
public class MeetingStartTimeComparator implements Comparator<BookingItem>{
    @Override
    public int compare(BookingItem o1, BookingItem o2) {
        return o1.getMeetingStartTime().compareTo(o2.getMeetingStartTime());
    }
}
