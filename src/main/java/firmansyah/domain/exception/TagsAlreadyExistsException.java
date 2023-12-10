// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class TagsAlreadyExistsException extends BusinessException {

	public TagsAlreadyExistsException() {
		super(2, "tags already exists");
	}
}
