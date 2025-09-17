package com.userinfo.model;
import java.time.LocalDateTime;
public class UserInfo {

	  private String name;
	    private String role;
	    private String environment;
	    private LocalDateTime timestamp;

	    public UserInfo(String name, String role, String environment, LocalDateTime timestamp) {
	        this.name = name;
	        this.role = role;
	        this.environment = environment;
	        this.timestamp = timestamp;
	    }

	    // Getters
	    public String getName() {
	        return name;
	    }

	    public String getRole() {
	        return role;
	    }

	    public String getEnvironment() {
	        return environment;
	    }

	    public LocalDateTime getTimestamp() {
	        return timestamp;
	    }

	    // Setters (optional, included for completeness if needed)
	    public void setName(String name) {
	        this.name = name;
	    }

	    public void setRole(String role) {
	        this.role = role;
	    }

	    public void setEnvironment(String environment) {
	        this.environment = environment;
	    }

	    public void setTimestamp(LocalDateTime timestamp) {
	        this.timestamp = timestamp;
	    }
	}
