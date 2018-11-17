**Test task (Taras Kobzar, statistics_library)**


**About**

This is a library implementation for collecting statistics on certain events.

**Notes**

Statistics provides the number of events for the last minute, hour, day. 
Calling a method to receive an event date does not block the thread used by this application library by using the ExecutorService. 
The complexity of obtaining statistics on events is O (1) and does not depend on the number of requests. 
This was achieved through the use of scheduled operations that modify statistics in real time (using the ScheduledExecutorService). 
The library does not provide clients with persistent event storage and event statistics.

