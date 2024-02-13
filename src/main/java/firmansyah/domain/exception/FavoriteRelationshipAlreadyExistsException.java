// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class FavoriteRelationshipAlreadyExistsException extends BusinessException {

	public FavoriteRelationshipAlreadyExistsException() {
		super(2, "favoriterelationship already exists");
	}
}
