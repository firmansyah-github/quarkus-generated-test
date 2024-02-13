// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class TagsAlreadyExistsException extends BusinessException {

	public TagsAlreadyExistsException() {
		super(2, "tags already exists");
	}
}
