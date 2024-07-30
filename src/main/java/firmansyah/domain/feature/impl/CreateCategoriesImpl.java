// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateCategories;
import firmansyah.domain.model.categories.*;
import firmansyah.domain.exception.CategoriesAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateCategoriesImpl implements CreateCategories {

	private final CategoriesRepository categoriesRepository;
	private final CategoriesModelBuilder categoriesBuilder;
	

	@Override
	public Categories handle(NewCategoriesInput newCategoriesInput) {
		final var categories =
			categoriesBuilder.build(newCategoriesInput.getCategoryId(),
					newCategoriesInput.getCategoryName(),
					newCategoriesInput.getDescription(),
					newCategoriesInput.getPicture(),
					null);
		
		if(categoriesRepository.findCategoriesByPrimaryKey(categories.getCategoryId()).isPresent()) {
			throw new CategoriesAlreadyExistsException();
		} else {
			categoriesRepository.save(categories);
		}
   
		return categories;
	}
}
