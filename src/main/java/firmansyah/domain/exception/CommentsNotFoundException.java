// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class CommentsNotFoundException extends BusinessException {

	public CommentsNotFoundException() {
		super(5, "comments not found");
	}
}
