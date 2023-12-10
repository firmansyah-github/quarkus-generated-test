// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class TagRelationshipNotFoundException extends BusinessException {

	public TagRelationshipNotFoundException() {
		super(5, "tagRelationship not found");
	}
}
