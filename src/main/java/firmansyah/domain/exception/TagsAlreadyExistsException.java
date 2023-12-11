// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class TagsAlreadyExistsException extends BusinessException {

	public TagsAlreadyExistsException() {
		super(2, "tags already exists");
	}
}
