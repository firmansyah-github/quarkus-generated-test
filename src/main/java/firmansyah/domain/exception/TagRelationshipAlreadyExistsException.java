// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class TagRelationshipAlreadyExistsException extends BusinessException {

	public TagRelationshipAlreadyExistsException() {
		super(2, "tagrelationship already exists");
	}
}
