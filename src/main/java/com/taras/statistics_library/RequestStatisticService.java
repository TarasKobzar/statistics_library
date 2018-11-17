package com.taras.statistics_library;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestStatisticService {
    private final static int MINUTE = TimeRangeType.MINUTE.getRange();
    private final static int HOUR = TimeRangeType.HOUR.getRange();
    private final static int DAY = TimeRangeType.DAY.getRange();

    private AsyncCounter minuteCounter = new AsyncCounter(MINUTE);
    private AsyncCounter hourCounter = new AsyncCounter(HOUR);
    private AsyncCounter dayCounter = new AsyncCounter(DAY);

    private ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    /**
     *Processes and takes into account the time of the event.
     * @param date the event time
     */
    public void handleRequest(Date date) {
        long ageOfTheEvent = System.currentTimeMillis() - date.getTime();

        if (ageOfTheEvent < DAY) {
            cachedThreadPool.submit(() -> {
                dayCounter.submit(date);
            });
        }
        if (ageOfTheEvent < HOUR) {
            cachedThreadPool.submit(() -> {
                hourCounter.submit(date);
            });
        }
        if (ageOfTheEvent < MINUTE) {
            cachedThreadPool.submit(() -> {
                minuteCounter.submit(date);
            });
        }
    }

    /**
     *Provides the number of events for a specified time interval.
     * @param timeRangeType the time range for which the number of events should be specified
     * @return a long representing the quantity of events in a time span
     */
    public long getQuantityOfEvents(TimeRangeType timeRangeType) {
        long quantityEvents = 0;

        switch (timeRangeType) {
            case MINUTE:
                quantityEvents = minuteCounter.size();
                break;
            case HOUR:
                quantityEvents = hourCounter.size();
                break;
            case DAY:
                quantityEvents = dayCounter.size();
                break;
        }
        return quantityEvents;
    }
}
