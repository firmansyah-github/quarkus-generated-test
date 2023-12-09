// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

public class FollowRelationshipAlreadyExistsException extends BusinessException {

	public FollowRelationshipAlreadyExistsException() {
		super(2, "followrelationship already exists");
	}
}
