// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class FollowRelationshipNotFoundException extends BusinessException {

	public FollowRelationshipNotFoundException() {
		super(5, "followRelationship not found");
	}
}
