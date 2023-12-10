// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class CommentsAlreadyExistsException extends BusinessException {

	public CommentsAlreadyExistsException() {
		super(2, "comments already exists");
	}
}
