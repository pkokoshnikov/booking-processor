package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.request.BookingItem;
import com.pkokoshnikov.bookingservice.model.response.ResponseDayBookingItems;

import java.util.List;

/**
 * User: pako1113
 * Date: 27.07.15
 */
public interface ResponsePacker{
    List<ResponseDayBookingItems> packResponse(List<BookingItem> bookingItems);
}
