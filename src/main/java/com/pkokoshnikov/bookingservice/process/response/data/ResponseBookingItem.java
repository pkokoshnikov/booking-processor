package com.pkokoshnikov.bookingservice.process.response.data;

import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.util.BookingUtils;
import com.pkokoshnikov.bookingservice.util.date.formatter.DateConstants;

import java.text.SimpleDateFormat;

/**
 * User: pako1113
 * Date: 13.07.15
 * This class provides model for response item
 */
public class ResponseBookingItem {
    private static SimpleDateFormat formatter = new SimpleDateFormat(DateConstants.RESPONSE_MEETING_START_END_TIME_FORMAT);

    private String meetingEndTime;
    private String meetingStartTime;
    private String userId;

    public ResponseBookingItem(BookingItem bookingItem) {
        meetingStartTime = formatter.format(bookingItem.getMeetingStartTime());
        meetingEndTime = formatter.format(BookingUtils.getMeetingEndTime(bookingItem));
        userId = bookingItem.getUserId();
    }

    public ResponseBookingItem() {
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
    public String toString() {
        return "ResponseBookingItem{" +
                "meetingEndTime='" + meetingEndTime + '\'' +
                ", meetingStartTime='" + meetingStartTime + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResponseBookingItem that = (ResponseBookingItem) o;

        if (meetingEndTime != null ? !meetingEndTime.equals(that.meetingEndTime) : that.meetingEndTime != null)
            return false;
        if (meetingStartTime != null ? !meetingStartTime.equals(that.meetingStartTime) : that.meetingStartTime != null)
            return false;
        return !(userId != null ? !userId.equals(that.userId) : that.userId != null);
    }
}
