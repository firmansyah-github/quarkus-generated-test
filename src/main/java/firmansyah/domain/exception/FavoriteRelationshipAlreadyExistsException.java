// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

public class FavoriteRelationshipAlreadyExistsException extends BusinessException {

	public FavoriteRelationshipAlreadyExistsException() {
		super(2, "favoriterelationship already exists");
	}
}
