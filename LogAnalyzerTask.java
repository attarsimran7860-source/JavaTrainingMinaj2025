package com.day7.lab1;
import java.util.List;
import java.util.concurrent.RecursiveTask;
public class LogAnalyzerTask  extends RecursiveTask<LogAnalysisResult> {
    private final List<LogEntry> logs;
    private final int start;
    private final int end;
    private final int THRESHOLD; // Adjustable threshold

    public LogAnalyzerTask(List<LogEntry> logs, int start, int end, int threshold) {
        this.logs = logs;
        this.start = start;
        this.end = end;
        this.THRESHOLD = threshold;
    }

    @Override
    protected LogAnalysisResult compute() {
        int length = end - start;

        if (length <= THRESHOLD) {
            
            return processSequential();
        } else {
            
            int mid = start + length / 2;
            LogAnalyzerTask leftTask = new LogAnalyzerTask(logs, start, mid, THRESHOLD);
            LogAnalyzerTask rightTask = new LogAnalyzerTask(logs, mid, end, THRESHOLD);

            
            leftTask.fork(); 

            LogAnalysisResult rightResult = rightTask.compute(); 

          
            LogAnalysisResult leftResult = leftTask.join();

           
            leftResult.merge(rightResult);
            return leftResult;
        }
    }

    private LogAnalysisResult processSequential() {
        LogAnalysisResult result = new LogAnalysisResult();
        for (int i = start; i < end; i++) {
            LogEntry entry = logs.get(i);
            result.addUserRequest(entry.getUserId());
            result.addUrlVisit(entry.getUrl());
            result.addResponseTime(entry.getResponseTime());
        }
        return result;
    }
}
