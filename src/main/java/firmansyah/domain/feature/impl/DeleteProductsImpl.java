// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteProducts;
import firmansyah.domain.model.products.ProductsRepository;


@AllArgsConstructor
public class DeleteProductsImpl implements DeleteProducts {

	private final ProductsRepository productsRepository;

  	@Override
	public boolean handle(Integer productId) {
		return productsRepository.delete(productId);
	}
}
