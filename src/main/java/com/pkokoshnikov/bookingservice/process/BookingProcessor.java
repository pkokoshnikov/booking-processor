package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;

import java.util.List;

/**
 * User: pako1113
 * Date: 08.07.15
 * This interface provides method for processing of batch meetings
 */
public interface BookingProcessor {
    List<GroupByDayBookingItem> processBatch(BookingBatch bookingBatch);
}
