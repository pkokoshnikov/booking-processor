package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.persistence.data.BookingItem;
import com.pkokoshnikov.bookingservice.controller.data.response.BookingItemResponse;
import com.pkokoshnikov.bookingservice.service.BookingService;
import lombok.NonNull;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Collectors;

@Service
public class BookingProcessor implements BookingService {
    @Override
    public @NonNull List<BookingItem> filterImpossibleMeetings(@NonNull List<BookingItem> bookingItems,@NonNull LocalTime startHour,
                                                      @NonNull LocalTime endHour) {
        val workTimeBookingPredicate = new WorkTimeBookingPredicate(startHour, endHour);
        val intersectionTimePredicate = new IntersectionTimePredicate(bookingItems);

        return bookingItems.stream()
                           .filter(workTimeBookingPredicate.and(intersectionTimePredicate))
                           .collect(Collectors.toList());
    }

    @Override
    public @NonNull SortedMap<LocalDate, List<BookingItemResponse>> groupByDay(@NonNull List<BookingItem> bookingItems) {
        return bookingItems.stream().collect(ProcessCollectors.groupByDateCollector());
    }
}
