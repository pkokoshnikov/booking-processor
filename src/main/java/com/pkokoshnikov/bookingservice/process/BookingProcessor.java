package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.request.BookingBatch;
import com.pkokoshnikov.bookingservice.model.request.BookingItem;

import java.util.List;

/**
 * User: pako1113
 * Date: 08.07.15
 * This interface provides method for processing of batch meetings
 */
public interface BookingProcessor {
    List<BookingItem> processBatch(BookingBatch bookingBatch);
}
