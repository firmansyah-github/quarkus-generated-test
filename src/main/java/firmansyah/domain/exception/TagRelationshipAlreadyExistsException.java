// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class TagRelationshipAlreadyExistsException extends BusinessException {

	public TagRelationshipAlreadyExistsException() {
		super(2, "tagrelationship already exists");
	}
}
