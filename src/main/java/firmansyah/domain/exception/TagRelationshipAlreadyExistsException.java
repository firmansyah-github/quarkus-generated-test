// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class TagRelationshipAlreadyExistsException extends BusinessException {

	public TagRelationshipAlreadyExistsException() {
		super(2, "tagrelationship already exists");
	}
}
