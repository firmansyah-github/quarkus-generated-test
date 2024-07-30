// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.categories.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateCategoriesImpl implements UpdateCategories {

	private final CategoriesRepository categoriesRepository;
	private final CategoriesModelBuilder categoriesBuilder;
	private final FindCategoriesByPrimaryKey findCategoriesByPrimaryKey;
	

	@Override
	public Categories handle(UpdateCategoriesInput updateCategoriesInput) {
		var categories = findCategoriesByPrimaryKey.handle(updateCategoriesInput.getCategoryId());
		
    	categories =
			categoriesBuilder.build(updateCategoriesInput.getCategoryId(),
					updateCategoriesInput.getCategoryName(),
					updateCategoriesInput.getDescription(),
					updateCategoriesInput.getPicture(),
					null);
		categoriesRepository.update(categories);
    
		return categories;
	}
}
