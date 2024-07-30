// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.products;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateProductsInput {
	private Integer productId;
	private String productName;
	private Integer supplierId;
	private Integer categoryId;
	private String quantityPerUnit;
	private Double unitPrice;
	private Integer unitsInStock;
	private Integer unitsOnOrder;
	private Integer reorderLevel;
	private Integer discontinued;
}
