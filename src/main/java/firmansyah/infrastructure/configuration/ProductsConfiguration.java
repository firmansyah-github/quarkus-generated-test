// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.products.ProductsModelBuilder;
import firmansyah.domain.model.products.ProductsRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class ProductsConfiguration {

	@Produces
  	@Singleton
  	public CreateProducts createProducts(
		ProductsRepository productsRepository,
      	ProductsModelBuilder productsBuilder ,FindCategoriesByPrimaryKey findCategoriesCategoryIdByPrimaryKey,FindSuppliersByPrimaryKey findSuppliersSupplierIdByPrimaryKey) {
    	return new CreateProductsImpl(
        	productsRepository,
        	productsBuilder ,findCategoriesCategoryIdByPrimaryKey,findSuppliersSupplierIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteProducts deleteProducts(
		ProductsRepository productsRepository) {
    		return new DeleteProductsImpl(
        			productsRepository);
  	}

  	@Produces
  	@Singleton
  	public FindProductsByFilter findProductsByFilter(ProductsRepository productsRepository) {
    	return new FindProductsByFilterImpl(productsRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindProductsByPrimaryKey findProductsByPrimaryKey(ProductsRepository productsRepository) {
		return new FindProductsByPrimaryKeyImpl(productsRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateProducts updateProducts(
		ProductsRepository productsRepository,
      	ProductsModelBuilder productsBuilder,
      	FindProductsByPrimaryKey findProductsByPrimaryKey	,FindCategoriesByPrimaryKey findCategoriesCategoryIdByPrimaryKey,FindSuppliersByPrimaryKey findSuppliersSupplierIdByPrimaryKey) {
		return new UpdateProductsImpl(
        	productsRepository,
        	productsBuilder,
        	findProductsByPrimaryKey ,findCategoriesCategoryIdByPrimaryKey,findSuppliersSupplierIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public ProductsModelBuilder productsBuilder(ModelValidator modelValidator) {
		return new ProductsModelBuilder(modelValidator);
  	}
}
