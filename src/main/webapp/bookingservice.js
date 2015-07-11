function BookingItem(requestSubmissionTime, meetingStartTime, userId, duration) {
    this.requestSubmissionTime = requestSubmissionTime;
    this.meetingStartTime = meetingStartTime;
    this.userId = userId;
    this.duration = duration;
}

function BookingBatch(startOfWorkDay, endOfWorkDay, bookingNodes) {
    this.startOfWorkDay = startOfWorkDay;
    this.endOfWorkDay = endOfWorkDay;
    this.bookingItems = bookingNodes;
}
