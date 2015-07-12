package com.pkokoshnikov.bookingservice.process;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.pkokoshnikov.bookingservice.model.BookingBatch;
import com.pkokoshnikov.bookingservice.model.BookingItem;
import com.pkokoshnikov.bookingservice.model.GroupByDayBookingItem;
import com.sun.istack.internal.Nullable;
import org.apache.commons.lang3.time.DateUtils;
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
    public List<GroupByDayBookingItem> processBatch(final BookingBatch bookingBatch) {
        logger.info("processBatch");
        List<BookingItem> bookingItems = Arrays.asList(bookingBatch.getBookingItems());

        //Filtering meetings which cannot be conducted in work hours
        List<BookingItem> filteredBookingItems = Lists.newArrayList(Iterables.filter(bookingItems, new Predicate<BookingItem>() {
            @Override
            public boolean apply(@Nullable BookingItem input) {
                return isWorkingHoursMeeting(input, bookingBatch.getStartOfWorkDay(), bookingBatch.getEndOfWorkDay());
            }
        }));

        //Sorting of meetings by request time submission
        Collections.sort(filteredBookingItems, new RequestSubmissionTimeComparator());
        List<BookingItem> approvedBookingItems = new LinkedList<>();

        for (final BookingItem bookingItem : filteredBookingItems) {
            //try to find intersecting meetings
            Optional<BookingItem> foundNode = Iterables.tryFind(approvedBookingItems, new Predicate<BookingItem>() {
                @Override
                public boolean apply(@Nullable BookingItem input) {
                    return isIntersected(input, bookingItem);
                }
            });
            //if we didn't find intersection we add it to approved list
            if (!foundNode.isPresent()) approvedBookingItems.add(bookingItem);
        }
        //Sorting of meetings by start time of meeting
        Collections.sort(approvedBookingItems, new MeetingStartTimeComparator());

        return groupByDate(approvedBookingItems);
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
    private boolean isIntersected(BookingItem currentNode, BookingItem checkNode) {
        long currentMeetingStartTime = currentNode.getMeetingStartTime().getTime();
        long currentMeetingEndTime = currentNode.getMeetingEndTime().getTime();

        long checkMeetingStartTime = checkNode.getMeetingStartTime().getTime();
        long checkMeetingEndTime = checkNode.getMeetingEndTime().getTime();

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

    private List<GroupByDayBookingItem> groupByDate(List<BookingItem> bookingItems) {
        List<GroupByDayBookingItem> groupByDayBookingItems = new ArrayList<>();
        if (bookingItems.isEmpty()) return groupByDayBookingItems;

        Date currentDay = bookingItems.get(0).getMeetingStartTime();
        GroupByDayBookingItem groupByDayBookingItem = new GroupByDayBookingItem();
        groupByDayBookingItem.setDateOfMeeting(currentDay);

        for (BookingItem bookingItem : bookingItems) {
            if (DateUtils.isSameDay(currentDay, bookingItem.getMeetingStartTime())) {
                groupByDayBookingItem.addBookingItem(bookingItem);
            } else {
                groupByDayBookingItems.add(groupByDayBookingItem);

                currentDay = bookingItem.getMeetingStartTime();
                groupByDayBookingItem = new GroupByDayBookingItem();
                groupByDayBookingItem.setDateOfMeeting(currentDay);
                groupByDayBookingItem.addBookingItem(bookingItem);
            }
        }

        groupByDayBookingItems.add(groupByDayBookingItem);

        return groupByDayBookingItems;
    }

    /**
     * This method detects that meeting is booked in work hours
     * @param bookingItem
     * @param workingStartTime
     * @param workingEndTime
     * @return
     */
    private boolean isWorkingHoursMeeting(BookingItem bookingItem, String workingStartTime, String workingEndTime) {
        long meetingStartTime = bookingItem.getMeetingStartTime().getTime();
        long meetingEndTime = bookingItem.getMeetingEndTime().getTime();

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
