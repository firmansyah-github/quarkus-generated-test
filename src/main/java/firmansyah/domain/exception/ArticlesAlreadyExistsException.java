// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class ArticlesAlreadyExistsException extends BusinessException {

	public ArticlesAlreadyExistsException() {
		super(2, "articles already exists");
	}
}
