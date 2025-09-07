package com.day2.lab1;

public interface Role {

	 void accessResource(String resource);

	 boolean canAccess(String resource);
     String getRoleName();
}
