package com.pkokoshnikov.bookingservice.process.response;

import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.process.response.data.ResponseDayBookingItems;

import java.util.List;

/**
 * User: pako1113
 * Date: 27.07.15
 */
public interface ResponsePacker{
    List<ResponseDayBookingItems> packResponse(List<BookingItem> bookingItems);
}
