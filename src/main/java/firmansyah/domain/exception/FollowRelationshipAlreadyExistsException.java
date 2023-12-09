// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

public class FollowRelationshipAlreadyExistsException extends BusinessException {

	public FollowRelationshipAlreadyExistsException() {
		super(2, "followrelationship already exists");
	}
}
