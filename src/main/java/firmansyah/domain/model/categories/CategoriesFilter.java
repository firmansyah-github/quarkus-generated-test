// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.categories;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.products.Products;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class CategoriesFilter {
	private final int offset;
	private final int limit;
	private Integer categoryId;
	private String categoryName;
	private String description;
	private byte[] picture;
}
