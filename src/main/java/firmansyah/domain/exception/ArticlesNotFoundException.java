// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

public class ArticlesNotFoundException extends BusinessException {

	public ArticlesNotFoundException() {
		super(5, "articles not found");
	}
}
