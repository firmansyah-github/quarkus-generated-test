// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

public class TagRelationshipNotFoundException extends BusinessException {

	public TagRelationshipNotFoundException() {
		super(5, "tagRelationship not found");
	}
}
