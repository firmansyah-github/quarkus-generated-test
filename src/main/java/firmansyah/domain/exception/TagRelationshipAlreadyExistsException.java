// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

public class TagRelationshipAlreadyExistsException extends BusinessException {

	public TagRelationshipAlreadyExistsException() {
		super(2, "tagrelationship already exists");
	}
}
