// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.products;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface ProductsRepository {

	void save(Products products);

	Optional<Products> findProductsByPrimaryKey(Integer productId);

	void update(Products products);

	boolean delete(Integer productId);

    PageResult<Products> findProductsByFilter(ResourceFilter resourceFilter);
    
	long countProducts();
}
