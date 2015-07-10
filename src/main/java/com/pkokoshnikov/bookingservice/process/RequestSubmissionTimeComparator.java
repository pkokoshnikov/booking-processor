package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.BookingNode;

import java.util.Comparator;

/**
 * User: pako1113
 * Date: 09.07.15
 */
public class RequestSubmissionTimeComparator implements Comparator<BookingNode> {
    @Override
    public int compare(BookingNode o1, BookingNode o2) {
        return o1.getRequestSubmissionTime().compareTo(o2.getRequestSubmissionTime());
    }
}
