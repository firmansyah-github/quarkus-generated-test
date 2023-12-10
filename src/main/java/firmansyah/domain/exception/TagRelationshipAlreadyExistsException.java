// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class TagRelationshipAlreadyExistsException extends BusinessException {

	public TagRelationshipAlreadyExistsException() {
		super(2, "tagrelationship already exists");
	}
}
