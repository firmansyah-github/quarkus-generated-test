// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.categories;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewCategoriesInput {
	private Integer categoryId;
	private String categoryName;
	private String description;
	private byte[] picture;
}
