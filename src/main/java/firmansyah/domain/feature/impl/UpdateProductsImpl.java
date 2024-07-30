// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.products.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateProductsImpl implements UpdateProducts {

	private final ProductsRepository productsRepository;
	private final ProductsModelBuilder productsBuilder;
	private final FindProductsByPrimaryKey findProductsByPrimaryKey;
	private final FindCategoriesByPrimaryKey findCategoriesCategoryIdByPrimaryKey;
	private final FindSuppliersByPrimaryKey findSuppliersSupplierIdByPrimaryKey;
	

	@Override
	public Products handle(UpdateProductsInput updateProductsInput) {
		var products = findProductsByPrimaryKey.handle(updateProductsInput.getProductId());
		
    	products =
			productsBuilder.build(updateProductsInput.getProductId(),
					updateProductsInput.getProductName(),
					updateProductsInput.getQuantityPerUnit(),
					updateProductsInput.getUnitPrice(),
					updateProductsInput.getUnitsInStock(),
					updateProductsInput.getUnitsOnOrder(),
					updateProductsInput.getReorderLevel(),
					updateProductsInput.getDiscontinued(),
					null,
					findCategoriesCategoryIdByPrimaryKey.handle(updateProductsInput.getCategoryId()),
					findSuppliersSupplierIdByPrimaryKey.handle(updateProductsInput.getSupplierId()));
		productsRepository.update(products);
    
		return products;
	}
}
