package org.example.realworldapi.domain.exception;

public class FavoriteRelationshipAlreadyExistsException extends BusinessException {

	public FavoriteRelationshipAlreadyExistsException() {
		super(2, "favoriterelationship already exists");
	}
}
