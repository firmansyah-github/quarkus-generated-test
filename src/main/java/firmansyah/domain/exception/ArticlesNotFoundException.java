// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class ArticlesNotFoundException extends BusinessException {

	public ArticlesNotFoundException() {
		super(5, "articles not found");
	}
}
