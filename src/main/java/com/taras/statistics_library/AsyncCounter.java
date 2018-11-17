package com.taras.statistics_library;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

class AsyncCounter {
    private AtomicLong counter = new AtomicLong();
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private int rangeOfTime;

    AsyncCounter(int range) {
        this.rangeOfTime = range;
    }

    void submit(Date date){
        counter.incrementAndGet();

        long ageOfTheEvent = System.currentTimeMillis() - date.getTime();

        executorService.schedule(() -> counter.decrementAndGet(), rangeOfTime - ageOfTheEvent, TimeUnit.MILLISECONDS);
    }

    long size(){
        return counter.get();
    }
}
