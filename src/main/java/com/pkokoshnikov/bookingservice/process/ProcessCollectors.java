package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.controller.model.response.BookingItemResponse;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * User: pako
 * Date: 16.03.18
 */
public class ProcessCollectors {
    public static Collector<BookingItem, ?, SortedMap<LocalDate, List<BookingItemResponse>>> groupByDateCollector() {
        return Collectors.groupingBy(bookingItem -> LocalDate.from(bookingItem.getMeetingStartTime()),
                                     TreeMap::new, //TreeMap to sort results by LocaleDate and to be sure in order
                                     mapToResponseListAndSort());
    }

    /**
     * @return Collector which maps BookingItem -> BookingItemResponse and sorts it by start meeting time
     */
    private static Collector<BookingItem, ?, List<BookingItemResponse>> mapToResponseListAndSort() {
        return Collectors.mapping(BookingItemResponse::new,
                                  Collectors.collectingAndThen(Collectors.toCollection(ArrayList::new), list -> {
                                      list.sort(Comparator.comparing(BookingItemResponse::getMeetingStartTime));
                                      return list;
                                  }));
    }
}
