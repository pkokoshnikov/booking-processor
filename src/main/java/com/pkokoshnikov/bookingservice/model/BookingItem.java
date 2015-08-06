package com.pkokoshnikov.bookingservice.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.pkokoshnikov.bookingservice.util.date.formatter.MeetingStartDateDeserializer;
import com.pkokoshnikov.bookingservice.util.date.formatter.MeetingStartDateSerializer;
import com.pkokoshnikov.bookingservice.util.date.formatter.RequestSubmissionDateDeserializer;
import com.pkokoshnikov.bookingservice.util.date.formatter.RequestSubmissionDateSerializer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * User: pako1113
 * Date: 07.07.15
 * This class provides POJO model for booking items
 */
@Entity
@Table(name = "bookings")
@NamedQueries({
    @NamedQuery(name="BookingItem.findAll", query="SELECT bi FROM BookingItem bi"),
    @NamedQuery(name="BookingItem.findByUserId", query="SELECT bi FROM BookingItem bi WHERE bi.userId = :userId"),
    @NamedQuery(name="BookingItem.findByDate", query="SELECT bi FROM BookingItem bi WHERE bi.meetingStartTime = :userId")
})
@JsonIgnoreProperties({"id"})
public class BookingItem implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name ="request_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = RequestSubmissionDateDeserializer.class)
    @JsonSerialize(using = RequestSubmissionDateSerializer.class)
    private Date requestSubmissionTime;

    @Column(name ="meeting_time")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonSerialize(using = MeetingStartDateSerializer.class)
    @JsonDeserialize(using = MeetingStartDateDeserializer.class)
    private Date meetingStartTime;

    @Column(name = "user_id")
    private String userId;
    @Column(name = "duration")
    private Integer duration;

    public BookingItem(Date requestSubmissionTime, Date meetingStartTime, String userId, Integer duration) {
        this.requestSubmissionTime = requestSubmissionTime;
        this.meetingStartTime = meetingStartTime;
        this.userId = userId;
        this.duration = duration;
    }

    public BookingItem() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BookingItem that = (BookingItem) o;

        if (duration != null ? !duration.equals(that.duration) : that.duration != null) return false;
        if (meetingStartTime != null ? !meetingStartTime.equals(that.meetingStartTime) : that.meetingStartTime != null)
            return false;
        if (requestSubmissionTime != null ? !requestSubmissionTime.equals(that.requestSubmissionTime) : that.requestSubmissionTime != null)
            return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = requestSubmissionTime != null ? requestSubmissionTime.hashCode() : 0;
        result = 31 * result + (meetingStartTime != null ? meetingStartTime.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        return result;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
