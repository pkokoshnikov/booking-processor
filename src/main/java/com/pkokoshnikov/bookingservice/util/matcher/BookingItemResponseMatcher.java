package com.pkokoshnikov.bookingservice.util.matcher;

import com.pkokoshnikov.bookingservice.controller.data.response.BookingItemResponse;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import java.time.LocalTime;

import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

/**
 * User: pako
 * Date: 17.03.18
 */
public class BookingItemResponseMatcher {
    public static Matcher<BookingItemResponse> meetingStartTime(LocalTime expected) {
        return new FeatureMatcher<BookingItemResponse, LocalTime>(is(expected), "meetingStartTime", "meetingStartTime") {
            @Override
            protected LocalTime featureValueOf(BookingItemResponse bookingItem) {
                return bookingItem.getMeetingStartTime();
            }
        };
    }

    public static Matcher<BookingItemResponse> meetingStartTimeIs(LocalTime expected) {
        return new FeatureMatcher<BookingItemResponse, LocalTime>(is(expected), "meetingEndTime", "meetingEndTime") {
            @Override
            protected LocalTime featureValueOf(BookingItemResponse bookingItem) {
                return bookingItem.getMeetingEndTime();
            }
        };
    }

    public static Matcher<BookingItemResponse> userIdIs(String userId) {
        return new FeatureMatcher<BookingItemResponse, String>(is(userId), "userId", "userId") {
            @Override
            protected String featureValueOf(BookingItemResponse bookingItem) {
                return bookingItem.getUserId();
            }
        };
    }

    public static Matcher<BookingItemResponse> equalsTo(BookingItemResponse expectedItem) {
        return allOf(meetingStartTime(expectedItem.getMeetingStartTime()), meetingStartTimeIs(expectedItem.getMeetingEndTime()), userIdIs(expectedItem.getUserId()));
    }
}
