package org.example.realworldapi.domain.exception;

public class CommentsNotFoundException extends BusinessException {

	public CommentsNotFoundException() {
		super(5, "comments not found");
	}
}
