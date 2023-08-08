package org.example.realworldapi.domain.exception;

public class TagsAlreadyExistsException extends BusinessException {

	public TagsAlreadyExistsException() {
		super(2, "tags already exists");
	}
}
