// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class ProductsAlreadyExistsException extends BusinessException {

	public ProductsAlreadyExistsException() {
		super(2, "products already exists");
	}
}
