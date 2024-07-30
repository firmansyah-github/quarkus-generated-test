// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CategoriesAlreadyExistsException extends BusinessException {

	public CategoriesAlreadyExistsException() {
		super(2, "categories already exists");
	}
}
