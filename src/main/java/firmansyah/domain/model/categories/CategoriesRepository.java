// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.categories;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface CategoriesRepository {

	void save(Categories categories);

	Optional<Categories> findCategoriesByPrimaryKey(Integer categoryId);

	void update(Categories categories);

	boolean delete(Integer categoryId);

    PageResult<Categories> findCategoriesByFilter(ResourceFilter resourceFilter);
    
	long countCategories();
}
