// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.products;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.model.suppliers.Suppliers;
import java.util.UUID;

@AllArgsConstructor
public class ProductsModelBuilder {

	private final ModelValidator modelValidator;

	public Products build(Integer productId, String productName, String quantityPerUnit, Double unitPrice, Integer unitsInStock, Integer unitsOnOrder, Integer reorderLevel, Integer discontinued, List<OrderDetails> orderDetailsProductIdList, Categories categoriesCategoryId, Suppliers suppliersSupplierId) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Products(productId, productName, quantityPerUnit, unitPrice, unitsInStock, unitsOnOrder, reorderLevel, discontinued, orderDetailsProductIdList, categoriesCategoryId, suppliersSupplierId));
	}
  
}
