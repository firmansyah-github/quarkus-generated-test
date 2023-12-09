// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

public class CommentsNotFoundException extends BusinessException {

	public CommentsNotFoundException() {
		super(5, "comments not found");
	}
}
