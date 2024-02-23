// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

public class TagRelationshipNotFoundException extends BusinessException {

	public TagRelationshipNotFoundException() {
		super(5, "tagRelationship not found");
	}
}
