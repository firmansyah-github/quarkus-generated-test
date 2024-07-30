// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteCategories;
import firmansyah.domain.model.categories.CategoriesRepository;


@AllArgsConstructor
public class DeleteCategoriesImpl implements DeleteCategories {

	private final CategoriesRepository categoriesRepository;

  	@Override
	public boolean handle(Integer categoryId) {
		return categoriesRepository.delete(categoryId);
	}
}
