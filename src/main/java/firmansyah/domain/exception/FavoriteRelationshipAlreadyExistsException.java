// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.exception;

public class FavoriteRelationshipAlreadyExistsException extends BusinessException {

	public FavoriteRelationshipAlreadyExistsException() {
		super(2, "favoriterelationship already exists");
	}
}
