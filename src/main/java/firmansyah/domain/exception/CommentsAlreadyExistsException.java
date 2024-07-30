// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CommentsAlreadyExistsException extends BusinessException {

	public CommentsAlreadyExistsException() {
		super(2, "comments already exists");
	}
}
