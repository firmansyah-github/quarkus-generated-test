// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.categories.CategoriesModelBuilder;
import firmansyah.domain.model.categories.CategoriesRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class CategoriesConfiguration {

	@Produces
  	@Singleton
  	public CreateCategories createCategories(
		CategoriesRepository categoriesRepository,
      	CategoriesModelBuilder categoriesBuilder ) {
    	return new CreateCategoriesImpl(
        	categoriesRepository,
        	categoriesBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteCategories deleteCategories(
		CategoriesRepository categoriesRepository) {
    		return new DeleteCategoriesImpl(
        			categoriesRepository);
  	}

  	@Produces
  	@Singleton
  	public FindCategoriesByFilter findCategoriesByFilter(CategoriesRepository categoriesRepository) {
    	return new FindCategoriesByFilterImpl(categoriesRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindCategoriesByPrimaryKey findCategoriesByPrimaryKey(CategoriesRepository categoriesRepository) {
		return new FindCategoriesByPrimaryKeyImpl(categoriesRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateCategories updateCategories(
		CategoriesRepository categoriesRepository,
      	CategoriesModelBuilder categoriesBuilder,
      	FindCategoriesByPrimaryKey findCategoriesByPrimaryKey	) {
		return new UpdateCategoriesImpl(
        	categoriesRepository,
        	categoriesBuilder,
        	findCategoriesByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public CategoriesModelBuilder categoriesBuilder(ModelValidator modelValidator) {
		return new CategoriesModelBuilder(modelValidator);
  	}
}
