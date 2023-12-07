// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.exception;

public class FavoriteRelationshipNotFoundException extends BusinessException {

	public FavoriteRelationshipNotFoundException() {
		super(5, "favoriteRelationship not found");
	}
}
