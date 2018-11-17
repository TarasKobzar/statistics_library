package com.taras.statistics_library;

import org.junit.Test;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class SimpleIntegrationTests {
    private RequestStatisticService requestStatisticService = new RequestStatisticService();
    private static Date currentDate = new Date();
    private static final long TIME_10_SECOND_AGO = currentDate.getTime() - 10_000;
    private static final long TIME_10_MINUTES_AGO = currentDate.getTime() - 600_000;
    private static final long TIME_10_HOURS_AGO = currentDate.getTime() - 36_000_000;
    private static final long TIME_30_HOURS_AGO = currentDate.getTime() - 108_000_000;

    @Test
    public void testQuantityOfEventsWithDate10SecondsAgo(){
        sendEventsDates(TIME_10_SECOND_AGO);

        waitForOneSec();

        assertEquals(10_000L, getQuantityOfEventsPerMinute());
        assertEquals(10_000L, getQuantityOfEventsPerHour());
        assertEquals(10_000L, getQuantityOfEventsPerDay());
    }

    @Test
    public void testQuantityOfEventsWithDate10MinutesAgo(){
        sendEventsDates(TIME_10_MINUTES_AGO);

        waitForOneSec();

        assertEquals(0L, getQuantityOfEventsPerMinute());
        assertEquals(10_000L, getQuantityOfEventsPerHour());
        assertEquals(10_000L, getQuantityOfEventsPerDay());
    }

    @Test
    public void testQuantityOfEventsWithDate10HoursAgo(){
        sendEventsDates(TIME_10_HOURS_AGO);

        waitForOneSec();

        assertEquals(0L, getQuantityOfEventsPerMinute());
        assertEquals(0L, getQuantityOfEventsPerHour());
        assertEquals(10_000L, getQuantityOfEventsPerDay());
    }

    @Test
    public void testQuantityOfEventsWithDate30HoursAgo(){
        sendEventsDates(TIME_30_HOURS_AGO);

        waitForOneSec();

        assertEquals(0L, getQuantityOfEventsPerMinute());
        assertEquals(0L, getQuantityOfEventsPerHour());
        assertEquals(0L, getQuantityOfEventsPerDay());
    }

    private void sendEventsDates(long date){
        new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                requestStatisticService.handleRequest(new Date(date));
            }
        }).start();
    }

    private long getQuantityOfEventsPerMinute(){
        return requestStatisticService.getQuantityOfEvents(TimeRangeType.MINUTE);
    }

    private long getQuantityOfEventsPerHour(){
        return requestStatisticService.getQuantityOfEvents(TimeRangeType.HOUR);
    }

    private long getQuantityOfEventsPerDay(){
        return requestStatisticService.getQuantityOfEvents(TimeRangeType.DAY);
    }

    private void waitForOneSec() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
