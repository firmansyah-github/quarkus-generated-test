// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

public class CommentsAlreadyExistsException extends BusinessException {

	public CommentsAlreadyExistsException() {
		super(2, "comments already exists");
	}
}
