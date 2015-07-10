package com.pkokoshnikov.bookingservice.process;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingNode;
import com.sun.istack.internal.Nullable;
import org.apache.log4j.Logger;

import java.util.*;

/**
 * User: pako1113
 * Date: 08.07.15
 */
public class BookingProcessorImpl implements BookingProcessor {
    final static Logger logger = Logger.getLogger(BookingProcessorImpl.class);
    final Calendar cal = Calendar.getInstance();

    @Override
    public List<BookingNode> processBatch(final BookingBatch bookingBatch) {
        logger.info("processBatch");
        List<BookingNode> bookingNodes = Arrays.asList(bookingBatch.getBookingNodes());

        //Filtering meetings which cannot be conducted in work hours
        List<BookingNode> filteredNodes = Lists.newArrayList(Iterables.filter(bookingNodes, new Predicate<BookingNode>() {
            @Override
            public boolean apply(@Nullable BookingNode input) {
                return isWorkingHoursMeeting(input, bookingBatch.getStartOfWorkDay(), bookingBatch.getEndOfWorkDay());
            }
        }));

        //Sorting of meetings by request time submission
        Collections.sort(filteredNodes, new RequestSubmissionTimeComparator());
        List<BookingNode> successFullBookingList = new LinkedList<>();


        for (final BookingNode bookingNode : filteredNodes) {
            //try to find intersecting meetings
            Optional<BookingNode> foundNode = Iterables.tryFind(successFullBookingList, new Predicate<BookingNode>() {
                @Override
                public boolean apply(@Nullable com.pkokoshnikov.bookingservice.model.BookingNode input) {
                    return isIntersected(input, bookingNode);
                }
            });
            //if we didn't find intersection we add it to success list
            if (!foundNode.isPresent()) successFullBookingList.add(bookingNode);
        }

        return successFullBookingList;
    }

    /**
     * This method checks intersection of two Booking Node by meeting time.
     * We have four cases
     * 1)
     * |---------|
     *       |---------|
     * <p/>
     * 2)
     *       |---------|
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
    private boolean isIntersected(BookingNode currentNode, BookingNode checkNode) {
        long currentMeetingStartTime = currentNode.getMeetingStartTime().getTime();
        long currentMeetingEndTime = getMeetingEndTime(currentNode.getMeetingStartTime(), currentNode.getDuration()).getTime();

        long checkMeetingStartTime = checkNode.getMeetingStartTime().getTime();
        long checkMeetingEndTime = getMeetingEndTime(checkNode.getMeetingStartTime(), checkNode.getDuration()).getTime();

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
     * @param bookingNode
     * @param workingStartTime
     * @param workingEndTime
     * @return
     */
    private boolean isWorkingHoursMeeting(BookingNode bookingNode, String workingStartTime, String workingEndTime) {
        long meetingStartTime = bookingNode.getMeetingStartTime().getTime();
        long meetingEndTime = getMeetingEndTime(bookingNode.getMeetingStartTime(), bookingNode.getDuration()).getTime();

        cal.setTime(bookingNode.getMeetingStartTime());
        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(workingStartTime).intValue() / 100);
        cal.set(Calendar.MINUTE, Integer.valueOf(workingStartTime).intValue() % 100);
        long lWorkingStartTime = cal.getTimeInMillis();

        cal.set(Calendar.HOUR_OF_DAY,Integer.valueOf(workingEndTime).intValue() / 100);
        cal.set(Calendar.MINUTE, Integer.valueOf(workingEndTime).intValue() % 100);
        long lWorkingEndTime = cal.getTimeInMillis();

        if (meetingStartTime < lWorkingStartTime) {
            return false;
        } else if (meetingEndTime > lWorkingEndTime) {
            return false;
        }

        return true;
    }

    private Date getMeetingEndTime(Date date, int duration) {
        cal.setTime(date);
        cal.add(Calendar.HOUR_OF_DAY, duration);

        return cal.getTime();
    }
}
