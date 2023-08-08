package org.example.realworldapi.domain.exception;

public class CommentsAlreadyExistsException extends BusinessException {

	public CommentsAlreadyExistsException() {
		super(2, "comments already exists");
	}
}
