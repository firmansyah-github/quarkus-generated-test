// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class CommentsAlreadyExistsException extends BusinessException {

	public CommentsAlreadyExistsException() {
		super(2, "comments already exists");
	}
}
