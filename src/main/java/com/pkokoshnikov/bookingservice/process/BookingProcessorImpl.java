package com.pkokoshnikov.bookingservice.process;

import com.pkokoshnikov.bookingservice.dao.BookingItemDAO;
import com.pkokoshnikov.bookingservice.dao.PropertiesDAO;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.util.BookingUtils;
import com.pkokoshnikov.bookingservice.util.comparators.MeetingStartTimeComparator;
import com.pkokoshnikov.bookingservice.util.comparators.RequestSubmissionTimeComparator;
import org.apache.log4j.Logger;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * User: pako1113
 * Date: 08.07.15
 */
public class BookingProcessorImpl implements BookingProcessor {
    private final static Logger logger = Logger.getLogger(BookingProcessorImpl.class);
    private final Calendar cal = Calendar.getInstance();
    private final PropertiesDAO propertiesDAO;
    private final BookingItemDAO bookingItemDAO;

    @Inject
    public BookingProcessorImpl(PropertiesDAO propertiesDAO, BookingItemDAO bookingItemDAO) {
        this.propertiesDAO = propertiesDAO;
        this.bookingItemDAO = bookingItemDAO;
    }

    @Override
    public List<BookingItem> processBatch(final BookingBatch bookingBatch) {
        logger.info("processBatch");
        
        List<BookingItem> bookingItems = Arrays.asList(bookingBatch.getBookingItems());        
        String startOfWorkDay = propertiesDAO.findPropertyByName("startWorkTime").getValue();
        String endOfWorkDay = propertiesDAO.findPropertyByName("endWorkTime").getValue();

        //filter bookingItems by work hours and then sort it by request submission time
        Stream<BookingItem> preparedStream = bookingItems.stream().filter(item -> isWorkingHoursMeeting(item, startOfWorkDay, endOfWorkDay))
                .sorted(new RequestSubmissionTimeComparator());
        
        List<BookingItem> approvedBookingItems = new LinkedList<>();

        //main part of processing booking items
        preparedStream.forEach(challengerItem -> {
            //try to find intersection with approved items
            boolean isIntersected = approvedBookingItems.stream().anyMatch(approvedItem -> isIntersected(approvedItem, challengerItem));
            //if we do not find intersection then we add the challenger to approved items
            if (!isIntersected) approvedBookingItems.add(challengerItem);
        }
        );

        //sort approved items by meeting start time
        List<BookingItem> sortedBookingItems = approvedBookingItems.stream().sorted(new MeetingStartTimeComparator()).collect(Collectors.toList());

        return sortedBookingItems;
    }

    /**
     * This method checks intersection of two Booking Node by meeting time.
     * We have four cases
     * 1)
     * |---------|
     *       |---------|
     * <p/>
     * 2)
     * |---------|
     * <p/>
     * 3)
     * |--------|
     * |-----------------|
     * <p/>
     * 4)
     * |------------------|
     * |---------|
     *
     * 5)
     * |---------|
     *           |---------|
     *
     * 6)
     *           |---------|
     * |---------|
     * <p/>
     * This method detects all these cases
     *
     * @param currentNode the current node which already exists in list of meetings
     * @param checkNode   the node to be checked
     * @return true if we have intersection and node cannot be added to list
     *         and false in we need to add this node to list
     */
    private boolean isIntersected(BookingItem currentNode, BookingItem checkNode) {
        long currentMeetingStartTime = currentNode.getMeetingStartTime().getTime();
        long currentMeetingEndTime = BookingUtils.getMeetingEndTime(currentNode).getTime();

        long checkMeetingStartTime = checkNode.getMeetingStartTime().getTime();
        long checkMeetingEndTime = BookingUtils.getMeetingEndTime(checkNode).getTime();

        if (currentMeetingEndTime == checkMeetingStartTime || checkMeetingEndTime == currentMeetingStartTime) {
            return false;
        }

        if (currentMeetingStartTime <= checkMeetingStartTime && currentMeetingEndTime >= checkMeetingStartTime ||
                currentMeetingStartTime <= checkMeetingEndTime && currentMeetingEndTime >= checkMeetingEndTime ||
                currentMeetingStartTime >= checkMeetingStartTime && currentMeetingEndTime <= checkMeetingEndTime) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * This method detects that meeting is booked in work hours
     *
     * @return true if meeting is booked in work hours
     */
    private boolean isWorkingHoursMeeting(BookingItem bookingItem, String workingStartTime, String workingEndTime) {
        long meetingStartTime = bookingItem.getMeetingStartTime().getTime();
        long meetingEndTime = BookingUtils.getMeetingEndTime(bookingItem).getTime();

        cal.setTime(bookingItem.getMeetingStartTime());
        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(workingStartTime) / 100);
        cal.set(Calendar.MINUTE, Integer.valueOf(workingStartTime) % 100);
        long lWorkingStartTime = cal.getTimeInMillis();

        cal.set(Calendar.HOUR_OF_DAY, Integer.valueOf(workingEndTime) / 100);
        cal.set(Calendar.MINUTE, Integer.valueOf(workingEndTime) % 100);
        long lWorkingEndTime = cal.getTimeInMillis();

        if (meetingStartTime < lWorkingStartTime) {
            return false;
        } else if (meetingEndTime > lWorkingEndTime) {
            return false;
        }

        return true;
    }
}
