// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

public class FollowRelationshipAlreadyExistsException extends BusinessException {

	public FollowRelationshipAlreadyExistsException() {
		super(2, "followrelationship already exists");
	}
}
