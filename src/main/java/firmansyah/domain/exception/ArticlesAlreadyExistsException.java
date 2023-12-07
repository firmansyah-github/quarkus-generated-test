// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.exception;

public class ArticlesAlreadyExistsException extends BusinessException {

	public ArticlesAlreadyExistsException() {
		super(2, "articles already exists");
	}
}
