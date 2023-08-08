package org.example.realworldapi.domain.exception;

public class SchoolNotFoundException extends BusinessException {

	public SchoolNotFoundException() {
		super(5, "school not found");
	}
}
