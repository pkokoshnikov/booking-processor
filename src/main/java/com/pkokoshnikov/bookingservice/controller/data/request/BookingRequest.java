package com.pkokoshnikov.bookingservice.controller.data.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pkokoshnikov.bookingservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

/**
 * User: pako
 * Date: 09.07.15
 * This class provides POJO model for input model of booking batch
 */
@AllArgsConstructor
@Getter
@ToString
public class BookingRequest implements Serializable {
    @JsonFormat(pattern = Constants.REQUEST_WORK_HOURS_FORMAT)
    private LocalTime startOfWorkDay;
    @JsonFormat(pattern = Constants.REQUEST_WORK_HOURS_FORMAT)
    private LocalTime endOfWorkDay;
    private List<BookingItemRequest> bookingItemRequests;
}
