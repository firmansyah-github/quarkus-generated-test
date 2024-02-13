// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class FavoriteRelationshipNotFoundException extends BusinessException {

	public FavoriteRelationshipNotFoundException() {
		super(5, "favoriteRelationship not found");
	}
}
