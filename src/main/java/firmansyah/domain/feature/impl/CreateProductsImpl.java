// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateProducts;
import firmansyah.domain.model.products.*;
import firmansyah.domain.exception.ProductsAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateProductsImpl implements CreateProducts {

	private final ProductsRepository productsRepository;
	private final ProductsModelBuilder productsBuilder;
	private final FindCategoriesByPrimaryKey findCategoriesCategoryIdByPrimaryKey;
	private final FindSuppliersByPrimaryKey findSuppliersSupplierIdByPrimaryKey;
	

	@Override
	public Products handle(NewProductsInput newProductsInput) {
		final var products =
			productsBuilder.build(newProductsInput.getProductId(),
					newProductsInput.getProductName(),
					newProductsInput.getQuantityPerUnit(),
					newProductsInput.getUnitPrice(),
					newProductsInput.getUnitsInStock(),
					newProductsInput.getUnitsOnOrder(),
					newProductsInput.getReorderLevel(),
					newProductsInput.getDiscontinued(),
					null,
					findCategoriesCategoryIdByPrimaryKey.handle(newProductsInput.getCategoryId()),
					findSuppliersSupplierIdByPrimaryKey.handle(newProductsInput.getSupplierId()));
		
		if(productsRepository.findProductsByPrimaryKey(products.getProductId()).isPresent()) {
			throw new ProductsAlreadyExistsException();
		} else {
			productsRepository.save(products);
		}
   
		return products;
	}
}
