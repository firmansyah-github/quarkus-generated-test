// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class TagRelationshipNotFoundException extends BusinessException {

	public TagRelationshipNotFoundException() {
		super(5, "tagRelationship not found");
	}
}
