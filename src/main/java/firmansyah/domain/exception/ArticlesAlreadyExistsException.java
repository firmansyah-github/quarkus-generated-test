// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

public class ArticlesAlreadyExistsException extends BusinessException {

	public ArticlesAlreadyExistsException() {
		super(2, "articles already exists");
	}
}
