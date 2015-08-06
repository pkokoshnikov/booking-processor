package com.pkokoshnikov.bookingservice.dao;

import com.pkokoshnikov.bookingservice.model.BookingItem;

import java.util.Date;
import java.util.List;

/**
 * User: pako1113
 * Date: 03.08.15
 */
public interface BookingItemDAO {
    void addBookingItem(BookingItem bookingItem);

    void addBookingItems(List<BookingItem> bookingItem);

    void deleteBookingItem(BookingItem bookingItem);

    void updateBookingItem(BookingItem bookingItem);

    BookingItem findBookingItem(long id);

    List<BookingItem> findBookingItemsByDate(Date date);

    List<BookingItem> findBookingItemsByUserId(String userId);
}
