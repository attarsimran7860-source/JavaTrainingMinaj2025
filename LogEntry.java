package com.day7.lab1;

public class LogEntry {

	private int userId;
    private String url;
    private int responseTime;

    public LogEntry(int userId, String url, int responseTime) {
        this.userId = userId;
        this.url = url;
        this.responseTime = responseTime;
    }

    public int getUserId() {
        return userId;
    }

    public String getUrl() {
        return url;
    }

    public int getResponseTime() {
        return responseTime;
    }

    @Override
    public String toString() {
        return "userId=" + userId + ", url=" + url + ", responseTime=" + responseTime;
    }
}