// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class TagsAlreadyExistsException extends BusinessException {

	public TagsAlreadyExistsException() {
		super(2, "tags already exists");
	}
}
