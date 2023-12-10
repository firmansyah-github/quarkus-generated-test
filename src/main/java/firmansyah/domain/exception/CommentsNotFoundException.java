// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class CommentsNotFoundException extends BusinessException {

	public CommentsNotFoundException() {
		super(5, "comments not found");
	}
}
