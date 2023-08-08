package org.example.realworldapi.domain.exception;

public class UsersNotFoundException extends BusinessException {

	public UsersNotFoundException() {
		super(5, "users not found");
	}
}
