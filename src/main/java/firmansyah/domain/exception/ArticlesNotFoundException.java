// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class ArticlesNotFoundException extends BusinessException {

	public ArticlesNotFoundException() {
		super(5, "articles not found");
	}
}
