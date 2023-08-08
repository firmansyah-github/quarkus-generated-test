package org.example.realworldapi.domain.exception;

public class TagRelationshipNotFoundException extends BusinessException {

	public TagRelationshipNotFoundException() {
		super(5, "tagRelationship not found");
	}
}
