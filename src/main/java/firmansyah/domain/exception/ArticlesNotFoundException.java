// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class ArticlesNotFoundException extends BusinessException {

	public ArticlesNotFoundException() {
		super(5, "articles not found");
	}
}
