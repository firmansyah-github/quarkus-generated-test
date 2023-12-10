// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class ArticlesNotFoundException extends BusinessException {

	public ArticlesNotFoundException() {
		super(5, "articles not found");
	}
}
