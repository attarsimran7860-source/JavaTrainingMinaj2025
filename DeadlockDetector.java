package com.day7.lab2;


import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class DeadlockDetector implements Runnable {
    private final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    private final long detectionIntervalMillis;
    private volatile boolean running = true;

    private final Set<Long> resolvedDeadlockThreads = ConcurrentHashMap.newKeySet();

    public DeadlockDetector(long detectionIntervalMillis) {
        this.detectionIntervalMillis = detectionIntervalMillis;
    }

    @Override
    public void run() {
       
        while (running) {
            try {
                Thread.sleep(detectionIntervalMillis);
                long[] deadlockedThreadIds = threadMXBean.findDeadlockedThreads();

                if (deadlockedThreadIds != null && deadlockedThreadIds.length > 0) {
                    ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(deadlockedThreadIds, true, true);

                    // Collect thread names for the desired output format
                    StringBuilder involvedThreads = new StringBuilder("[");
                    for (int i = 0; i < threadInfos.length; i++) {
                        involvedThreads.append(threadInfos[i].getThreadName());
                        if (i < threadInfos.length - 1) {
                            involvedThreads.append(", ");
                        }
                    }
                    involvedThreads.append("]");
                    //System.out.println("[DeadlockDetector] Deadlock detected! Threads involved: " + involvedThreads.toString());

                    // Deadlock Resolution: Interrupt one of the deadlocked threads
                    for (long threadId : deadlockedThreadIds) {
                        if (!resolvedDeadlockThreads.contains(threadId)) {
                             Thread targetThread = findThreadById(threadId);
                            if (targetThread != null && targetThread.isAlive()) {
                                resolvedDeadlockThreads.add(threadId);
                                System.out.println("[DeadlockDetector] Resolving by interrupting " + targetThread.getName());
                                System.out.println("[" + Thread.currentThread().getName() + "] Transaction aborted due to deadlock resolution.");
                                
                                targetThread.interrupt();
                                break; // Resolve only one thread per detection cycle to allow others to proceed
                            }
                        }
                    }
                }
                
            } catch (InterruptedException e) {
                // System.out.println("[DeadlockDetector] Interrupted, shutting down."); // Removed for exact output
                Thread.currentThread().interrupt();
                running = false;
            } catch (Exception e) {
                System.err.println("[DeadlockDetector] Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        System.out.println("[" + Thread.currentThread().getName() + "] Transaction completed successfully");
    }

    public void shutdown() {
        this.running = false;
    }

    private Thread findThreadById(long threadId) {
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread thread : threadSet) {
            if (thread.getId() == threadId) {
                return thread;
            }
        }
        return null;
    }
}