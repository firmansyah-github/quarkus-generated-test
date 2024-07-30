// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.products;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;


import firmansyah.domain.model.orderDetails.OrderDetails;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.model.suppliers.Suppliers;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Products {
	@NotNull(message = ValidationMessages.PRODUCTS_PRODUCTID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer productId;
	@NotBlank(message = ValidationMessages.PRODUCTS_PRODUCTNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.PRODUCTS_PRODUCTNAME_MAX_LENGTH, max = 40)
	private String productName;
	@Size(message = ValidationMessages.PRODUCTS_QUANTITYPERUNIT_MAX_LENGTH, max = 20)
	private String quantityPerUnit;
	@Max(8)
	private Double unitPrice;
	@Max(5)
	private Integer unitsInStock;
	@Max(5)
	private Integer unitsOnOrder;
	@Max(5)
	private Integer reorderLevel;
	@NotNull(message = ValidationMessages.PRODUCTS_DISCONTINUED_MUST_BE_NOT_BLANK)
	@Max(10)
	private Integer discontinued;
	private List<OrderDetails> orderDetailsProductIdList;
	
	private Categories categoriesCategoryId;
	private Suppliers suppliersSupplierId;
	
}
