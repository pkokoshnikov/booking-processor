package com.pkokoshnikov.bookingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkokoshnikov.bookingservice.util.date.CustomDateDeserializer;
import com.pkokoshnikov.bookingservice.util.date.CustomDateSerializer;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * User: pako1113
 * Date: 07.07.15
 */
public class BookingNode implements Serializable{

    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date requestSubmissionTime;

    @JsonSerialize(using = CustomDateSerializer.class)
    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date meetingStartTime;

    private String userId;
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
        return "BookingNode{" +
                "requestSubmissionTime=" + requestSubmissionTime +
                ", meetingStartTime=" + meetingStartTime +
                ", userId='" + userId + '\'' +
                ", duration=" + duration +
                '}';
    }
}
