package org.example.realworldapi.domain.exception;

public class FilterValueNotValidException extends BusinessException {

	public FilterValueNotValidException() {
		super(8, "filter value not valid");
	}
}
