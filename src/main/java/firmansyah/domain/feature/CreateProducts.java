// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.products.Products;
import firmansyah.domain.model.products.NewProductsInput;

public interface CreateProducts {
	Products handle(NewProductsInput newProductsInput);
}
