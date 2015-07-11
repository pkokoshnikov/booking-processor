package com.pkokoshnikov.bookingservice.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkokoshnikov.bookingservice.util.formatters.*;

import java.io.Serializable;
import java.util.Date;

/**
 * User: pako1113
 * Date: 07.07.15
 */
public class BookingItem implements Serializable{

    @JsonDeserialize(using = RequestSubmissionDateDeserializer.class)
    @JsonSerialize(using = RequestSubmissionDateSerializer.class)
    @JsonView(Views.Extended.class)
    private Date requestSubmissionTime;

    @JsonSerialize(using = MeetingStartDateSerializer.class)
    @JsonDeserialize(using = MeetingStartDateDeserializer.class)
    @JsonView(Views.Public.class)
    private Date meetingStartTime;

    @JsonView(Views.Public.class)
    private String userId;
    @JsonView(Views.Extended.class)
    private Integer duration;

    public Date getRequestSubmissionTime() {
        return requestSubmissionTime;
    }

    public void setRequestSubmissionTime(Date requestSubmissionTime) {
        this.requestSubmissionTime = requestSubmissionTime;
    }

    public Date getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(Date meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "BookingItem{" +
                "requestSubmissionTime=" + requestSubmissionTime +
                ", meetingStartTime=" + meetingStartTime +
                ", userId='" + userId + '\'' +
                ", duration=" + duration +
                '}';
    }

    public class Views {
        public class Public{}
        public class Extended{}
    }
}
