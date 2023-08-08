package org.example.realworldapi.domain.exception;

public class SchoolAlreadyExistsException extends BusinessException {

	public SchoolAlreadyExistsException() {
		super(2, "school already exists");
	}
}
