// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class FavoriteRelationshipNotFoundException extends BusinessException {

	public FavoriteRelationshipNotFoundException() {
		super(5, "favoriteRelationship not found");
	}
}
