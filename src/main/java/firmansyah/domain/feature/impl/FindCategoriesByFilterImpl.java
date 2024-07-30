// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindCategoriesByFilter;
import firmansyah.domain.model.categories.Categories;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.categories.CategoriesRepository;
import firmansyah.domain.model.util.PageResult;

@AllArgsConstructor
public class FindCategoriesByFilterImpl implements FindCategoriesByFilter {

	private final CategoriesRepository categoriesRepository;

	@Override
	public PageResult<Categories> handle(ResourceFilter resourceFilterr) {
		return categoriesRepository.findCategoriesByFilter(resourceFilterr);
	}
}
