// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindProductsByPrimaryKey;
import firmansyah.domain.exception.ProductsNotFoundException;
import firmansyah.domain.model.products.Products;
import firmansyah.domain.model.products.ProductsRepository;


@AllArgsConstructor
public class FindProductsByPrimaryKeyImpl implements FindProductsByPrimaryKey {

	private final ProductsRepository productsRepository;

	@Override
	public Products handle(Integer productId) {
		return productsRepository.findProductsByPrimaryKey(productId)
    			.orElseThrow(ProductsNotFoundException::new);
	}
}
