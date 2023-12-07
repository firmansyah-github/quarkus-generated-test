// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.exception;

public class FollowRelationshipNotFoundException extends BusinessException {

	public FollowRelationshipNotFoundException() {
		super(5, "followRelationship not found");
	}
}
