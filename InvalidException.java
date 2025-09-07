package com.day3.lab1;

public class InvalidException extends Exception{

	 private final int lineNumber;
	    private final String reason;

	    public InvalidException(String message, int lineNumber, String reason) {
	        super(message);
	        this.lineNumber = lineNumber;
	        this.reason = reason;
	    }

	    public int getLineNumber() {
	        return lineNumber;
	    }

	    public String getReason() {
	        return reason;
	    }

	    @Override
	    public String getMessage() {
	        return String.format("Invalid record at line %d: %s (Reason: %s)", lineNumber, super.getMessage(), reason);
	    }
	}