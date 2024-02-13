// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class FollowRelationshipNotFoundException extends BusinessException {

	public FollowRelationshipNotFoundException() {
		super(5, "followRelationship not found");
	}
}
