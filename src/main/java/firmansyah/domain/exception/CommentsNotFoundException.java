// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class CommentsNotFoundException extends BusinessException {

	public CommentsNotFoundException() {
		super(5, "comments not found");
	}
}
