// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindProductsByFilter;
import firmansyah.domain.model.products.Products;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.products.ProductsRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindProductsByFilterImpl implements FindProductsByFilter {

	private final ProductsRepository productsRepository;

	@Override
	public PageResult<Products> handle(ResourceFilter resourceFilterr) {
		return productsRepository.findProductsByFilter(resourceFilterr);
	}
}
