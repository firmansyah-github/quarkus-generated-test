// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class FavoriteRelationshipNotFoundException extends BusinessException {

	public FavoriteRelationshipNotFoundException() {
		super(5, "favoriteRelationship not found");
	}
}
