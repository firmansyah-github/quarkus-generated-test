// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.products.Products;
import firmansyah.domain.model.products.UpdateProductsInput;


public interface UpdateProducts {
	Products handle(UpdateProductsInput updateProductsInput);
}
