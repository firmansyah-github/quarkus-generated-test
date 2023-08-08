package org.example.realworldapi.domain.exception;

public class FilterFieldNotValidException extends BusinessException {

	public FilterFieldNotValidException() {
		super(6, "filter or sort field not valid");
	}
}
