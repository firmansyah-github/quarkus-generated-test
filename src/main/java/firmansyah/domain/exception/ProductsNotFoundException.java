// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class ProductsNotFoundException extends BusinessException {

	public ProductsNotFoundException() {
		super(5, "products not found");
	}
}
