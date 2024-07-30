// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCategoriesByPrimaryKey;
import firmansyah.domain.exception.CategoriesNotFoundException;
import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.model.categories.CategoriesRepository;


@AllArgsConstructor
public class FindCategoriesByPrimaryKeyImpl implements FindCategoriesByPrimaryKey {

	private final CategoriesRepository categoriesRepository;

	@Override
	public Categories handle(Integer categoryId) {
		return categoriesRepository.findCategoriesByPrimaryKey(categoryId)
    			.orElseThrow(CategoriesNotFoundException::new);
	}
}
