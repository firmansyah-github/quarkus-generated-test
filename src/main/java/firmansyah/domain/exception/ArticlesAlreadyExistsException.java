// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class ArticlesAlreadyExistsException extends BusinessException {

	public ArticlesAlreadyExistsException() {
		super(2, "articles already exists");
	}
}
