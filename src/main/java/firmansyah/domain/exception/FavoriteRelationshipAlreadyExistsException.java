// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

public class FavoriteRelationshipAlreadyExistsException extends BusinessException {

	public FavoriteRelationshipAlreadyExistsException() {
		super(2, "favoriterelationship already exists");
	}
}
