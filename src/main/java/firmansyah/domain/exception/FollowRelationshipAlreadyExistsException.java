// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class FollowRelationshipAlreadyExistsException extends BusinessException {

	public FollowRelationshipAlreadyExistsException() {
		super(2, "followrelationship already exists");
	}
}
