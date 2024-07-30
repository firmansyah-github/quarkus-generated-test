// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.categories;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.products.Products;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class CategoriesModelBuilder {

	private final ModelValidator modelValidator;

	public Categories build(Integer categoryId, String categoryName, String description, byte[] picture, List<Products> productsCategoryIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Categories(categoryId, categoryName, description, picture, productsCategoryIdList));
	}
  
}
