package com.pkokoshnikov.bookingservice.util.comparators;

import com.pkokoshnikov.bookingservice.model.request.BookingItem;

import java.util.Comparator;

/**
 * User: pako1113
 * Date: 09.07.15
 */
public class RequestSubmissionTimeComparator implements Comparator<BookingItem> {
    @Override
    public int compare(BookingItem o1, BookingItem o2) {
        return o1.getRequestSubmissionTime().compareTo(o2.getRequestSubmissionTime());
    }
}
