package org.example.realworldapi.domain.exception;

public class TagsNotFoundException extends BusinessException {

	public TagsNotFoundException() {
		super(5, "tags not found");
	}
}
