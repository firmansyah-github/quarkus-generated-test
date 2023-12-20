// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

public class FollowRelationshipAlreadyExistsException extends BusinessException {

	public FollowRelationshipAlreadyExistsException() {
		super(2, "followrelationship already exists");
	}
}
