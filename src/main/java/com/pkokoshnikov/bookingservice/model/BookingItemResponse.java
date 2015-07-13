package com.pkokoshnikov.bookingservice.model;

import com.pkokoshnikov.bookingservice.util.formatters.Constants;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User: pako1113
 * Date: 13.07.15
 */
public class BookingItemResponse {
    private final Calendar cal = Calendar.getInstance();
    private static SimpleDateFormat formatter = new SimpleDateFormat(Constants.RESPONSE_MEETING_START_END_TIME_FORMAT);

    private String meetingEndTime;
    private String meetingStartTime;
    private String userId;

    public BookingItemResponse(BookingItem bookingItem) {
        meetingStartTime = formatter.format(bookingItem.getMeetingStartTime());
        cal.setTime(bookingItem.getMeetingStartTime());
        cal.add(Calendar.HOUR_OF_DAY, bookingItem.getDuration());
        meetingEndTime = formatter.format(cal.getTime());
        userId = bookingItem.getUserId();
    }

    public BookingItemResponse() {
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public void setMeetingEndTime(String meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingItemResponse that = (BookingItemResponse) o;

        if (meetingEndTime != null ? !meetingEndTime.equals(that.meetingEndTime) : that.meetingEndTime != null)
            return false;
        if (meetingStartTime != null ? !meetingStartTime.equals(that.meetingStartTime) : that.meetingStartTime != null)
            return false;
        return !(userId != null ? !userId.equals(that.userId) : that.userId != null);
    }
}
