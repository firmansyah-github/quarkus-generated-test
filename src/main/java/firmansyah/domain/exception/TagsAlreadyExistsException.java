// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

public class TagsAlreadyExistsException extends BusinessException {

	public TagsAlreadyExistsException() {
		super(2, "tags already exists");
	}
}
