// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

public class FavoriteRelationshipNotFoundException extends BusinessException {

	public FavoriteRelationshipNotFoundException() {
		super(5, "favoriteRelationship not found");
	}
}
