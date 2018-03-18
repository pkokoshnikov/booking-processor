package com.pkokoshnikov.bookingservice.integration;

import com.pkokoshnikov.bookingservice.controller.model.request.BookingRequest;
import com.pkokoshnikov.bookingservice.controller.model.response.BookingItemResponse;
import com.pkokoshnikov.bookingservice.controller.model.response.BookingResponse;
import com.pkokoshnikov.bookingservice.model.ApprovedBooking;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.service.PersistenceService;
import lombok.val;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.SortedMap;

import static com.pkokoshnikov.bookingservice.util.Constants.*;
import static com.pkokoshnikov.bookingservice.util.matcher.BookingItemResponseMatcher.equalsTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.mockito.Mockito.when;

/**
 * User: pako
 * Date: 13.07.15
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingControllerTest {
    private static final String EMP_001 = "EMP_001";
    private static final BookingItem BOOKING_ITEM_0 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 10, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_1 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 21, 9, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_2 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 22, 10, 0),
                                                                      EMP_001, 1);
    private static final BookingItem BOOKING_ITEM_3 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 22, 16, 0),
                                                                      EMP_001, 2);
    private static final BookingItem BOOKING_ITEM_4 = new BookingItem(LocalDateTime.of(2011, 3, 17, 10, 17, 6), LocalDateTime.of(2011, 3, 22, 13, 0),
                                                                      EMP_001, 2);

    private static final LocalDate FIRST_DATE = LocalDate.of(2011, 3, 21);
    private static final LocalDate SECOND_DATE = LocalDate.of(2011, 3, 22);

    @MockBean
    private PersistenceService persistenceService;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void verifyLastApprovedBooking() {
        when(persistenceService.getLastApprovedBooking()).thenReturn(new ApprovedBooking(asList(BOOKING_ITEM_0, BOOKING_ITEM_1)));

        val lastBooking = getLastBooking();

        SortedMap<LocalDate, List<BookingItemResponse>> body = lastBooking.getBody().getResult();

        assertThat(body.keySet(), contains(FIRST_DATE));
        assertThat(body.get(FIRST_DATE), contains(asList(equalsTo(new BookingItemResponse(BOOKING_ITEM_1)), equalsTo(new BookingItemResponse(BOOKING_ITEM_0)))));
    }

    @Test
    public void verifyGetApprovedBooking() {
        Long id = 1l;
        when(persistenceService.getApprovedBooking(id)).thenReturn(new ApprovedBooking(asList(BOOKING_ITEM_0, BOOKING_ITEM_1)));

        val bookingResponse = getBooking(id);

        SortedMap<LocalDate, List<BookingItemResponse>> body = bookingResponse.getBody().getResult();

        assertThat(body.keySet(), contains(FIRST_DATE));
        assertThat(body.get(FIRST_DATE), contains(asList(equalsTo(new BookingItemResponse(BOOKING_ITEM_1)), equalsTo(new BookingItemResponse(BOOKING_ITEM_0)))));
    }

    @Test
    public void verifyProcessResponse() {
        BookingRequest bookingRequest = new BookingRequest(LocalTime.of(9, 0), LocalTime.of(17, 0), asList(BOOKING_ITEM_0, BOOKING_ITEM_1, BOOKING_ITEM_2, BOOKING_ITEM_3, BOOKING_ITEM_4));

        val bookingResponse = processBookings(bookingRequest);

        SortedMap<LocalDate, List<BookingItemResponse>> body = bookingResponse.getBody()
                                                                              .getResult();

        assertThat(body.keySet(), contains(FIRST_DATE, SECOND_DATE));
        assertThat(body.get(FIRST_DATE), contains(asList(equalsTo(new BookingItemResponse(BOOKING_ITEM_1)), equalsTo(new BookingItemResponse(BOOKING_ITEM_0)))));
        assertThat(body.get(SECOND_DATE), contains(asList(equalsTo(new BookingItemResponse(BOOKING_ITEM_2)), equalsTo(new BookingItemResponse(BOOKING_ITEM_4)))));
    }

    private ResponseEntity<BookingResponse> processBookings(BookingRequest bookingRequest) {
        return restTemplate.exchange(API_BOOKING + PROCESS_END_POINT, HttpMethod.POST, new HttpEntity<>(bookingRequest), BookingResponse.class);
    }

    private ResponseEntity<BookingResponse> getBooking(Long id) {
        return restTemplate.exchange(API_BOOKING + "/" + id, HttpMethod.GET, null, BookingResponse.class);
    }

    private ResponseEntity<BookingResponse> getLastBooking() {
        return restTemplate.exchange(API_BOOKING + LAST_BOOKING_END_POINT, HttpMethod.GET, null, BookingResponse.class);
    }

}

