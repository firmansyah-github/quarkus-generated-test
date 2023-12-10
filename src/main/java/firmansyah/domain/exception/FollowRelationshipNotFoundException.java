// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class FollowRelationshipNotFoundException extends BusinessException {

	public FollowRelationshipNotFoundException() {
		super(5, "followRelationship not found");
	}
}
