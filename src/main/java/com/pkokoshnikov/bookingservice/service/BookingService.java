package com.pkokoshnikov.bookingservice.service;

import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.controller.model.response.BookingItemResponse;
import lombok.NonNull;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.SortedMap;

/**
 * User: pako
 * Date: 16.03.18
 * This service provides interface to process with booking items
 */
public interface BookingService {
    @NonNull List<BookingItem> filterImpossibleMeetings(@NonNull List<BookingItem> bookingItems, @NonNull LocalTime startHour, @NonNull LocalTime endHour);

    @NonNull SortedMap<LocalDate, List<BookingItemResponse>> groupByDay(@NonNull List<BookingItem> bookingItems);
}
