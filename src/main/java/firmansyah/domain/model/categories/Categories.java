// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.categories;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.products.Products;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Categories {
	@NotNull(message = ValidationMessages.CATEGORIES_CATEGORYID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer categoryId;
	@NotBlank(message = ValidationMessages.CATEGORIES_CATEGORYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CATEGORIES_CATEGORYNAME_MAX_LENGTH, max = 15)
	private String categoryName;
	@Size(message = ValidationMessages.CATEGORIES_DESCRIPTION_MAX_LENGTH, max = 2147483647)
	private String description;
	private byte[] picture;
	private List<Products> productsCategoryIdList;
	
	
}
