// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

public class CommentsAlreadyExistsException extends BusinessException {

	public CommentsAlreadyExistsException() {
		super(2, "comments already exists");
	}
}
