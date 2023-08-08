package org.example.realworldapi.domain.exception;

public class FilterOperatorNotValidException extends BusinessException {

	public FilterOperatorNotValidException() {
		super(7, "filter operator not valid");
	}
}
