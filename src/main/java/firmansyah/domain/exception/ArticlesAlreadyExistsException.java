// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class ArticlesAlreadyExistsException extends BusinessException {

	public ArticlesAlreadyExistsException() {
		super(2, "articles already exists");
	}
}
