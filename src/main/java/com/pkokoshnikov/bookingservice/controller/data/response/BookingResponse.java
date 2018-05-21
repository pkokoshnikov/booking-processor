package com.pkokoshnikov.bookingservice.controller.data.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.SortedMap;

/**
 * User: pako
 * Date: 18.03.18
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BookingResponse {
    private Long id;
    private SortedMap<LocalDate, List<BookingItemResponse>> result;
}
