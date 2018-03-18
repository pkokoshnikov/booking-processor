package com.pkokoshnikov.bookingservice.controller;

import com.pkokoshnikov.bookingservice.model.ApprovedBooking;
import com.pkokoshnikov.bookingservice.controller.model.request.BookingRequest;
import com.pkokoshnikov.bookingservice.controller.model.response.BookingResponse;
import com.pkokoshnikov.bookingservice.service.BookingService;
import com.pkokoshnikov.bookingservice.service.PersistenceService;
import com.pkokoshnikov.bookingservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.pkokoshnikov.bookingservice.util.Constants.LAST_BOOKING_END_POINT;
import static com.pkokoshnikov.bookingservice.util.Constants.PROCESS_END_POINT;
import static com.pkokoshnikov.bookingservice.util.Constants.STATUS_END_POINT;

/**
 * User: pako
 * Date: 07.07.15
 */
@RestController
@Slf4j
@RequestMapping(Constants.API_BOOKING)
public class BookingController {
    private final BookingService bookingService;
    private final PersistenceService persistenceService;

    @Autowired
    public BookingController(BookingService bookingService, PersistenceService persistenceService) {
        this.bookingService = bookingService;
        this.persistenceService = persistenceService;
    }

    @RequestMapping(value = PROCESS_END_POINT, method = RequestMethod.POST, produces = "application/json")
    public BookingResponse processBooking(@RequestBody BookingRequest bookingRequest) {
        val filteredMeetings = bookingService.filterImpossibleMeetings(bookingRequest.getBookingItems(), bookingRequest.getStartOfWorkDay(), bookingRequest.getEndOfWorkDay());

        val id = persistenceService.storeApprovedBookings(new ApprovedBooking(filteredMeetings));

        return new BookingResponse(id, bookingService.groupByDay(filteredMeetings));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public BookingResponse getById(@PathVariable Long id) {
        val foundHistory = persistenceService.getApprovedBooking(id);

        return new BookingResponse(id, bookingService.groupByDay(foundHistory.getBookingItems()));
    }

    @RequestMapping(value = LAST_BOOKING_END_POINT, method = RequestMethod.GET, produces = "application/json")
    public BookingResponse getLast() {
        val foundHistory = persistenceService.getLastApprovedBooking();

        return new BookingResponse(foundHistory.getId(), bookingService.groupByDay(foundHistory.getBookingItems()));
    }

    @RequestMapping(value = STATUS_END_POINT, method = RequestMethod.GET)
    public ResponseEntity getStatusService() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
