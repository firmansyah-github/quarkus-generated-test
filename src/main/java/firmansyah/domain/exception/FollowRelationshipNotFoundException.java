// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

public class FollowRelationshipNotFoundException extends BusinessException {

	public FollowRelationshipNotFoundException() {
		super(5, "followRelationship not found");
	}
}
