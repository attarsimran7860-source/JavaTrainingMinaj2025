package com.day7.lab1;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
public class LogAnalyzerApp {

	private static final int NUMBER_OF_LOGS = 50000;
    private static final int PROCESSING_THRESHOLD = 1000;

    public static void main(String[] args) {
        

        List<LogEntry> logs = LogDataGenerator.generateLogs(NUMBER_OF_LOGS);

       
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
      

        long startTime = System.currentTimeMillis();
        LogAnalyzerTask mainTask = new LogAnalyzerTask(logs, 0, logs.size(), PROCESSING_THRESHOLD);
        LogAnalysisResult finalResult = forkJoinPool.invoke(mainTask); 

        long endTime = System.currentTimeMillis();

        finalResult.printResults();

    
    }
}
