// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.products;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class ProductsFilter {
	private final int offset;
	private final int limit;
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
