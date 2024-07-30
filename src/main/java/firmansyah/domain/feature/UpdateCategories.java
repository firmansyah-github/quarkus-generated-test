// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.model.categories.UpdateCategoriesInput;


public interface UpdateCategories {
	Categories handle(UpdateCategoriesInput updateCategoriesInput);
}
