// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.products.Products;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("products")
@RegisterForReflection
public class ProductsResponse {

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
	private CategoriesResponse categoriesCategoryIdResponse;
	private SuppliersResponse suppliersSupplierIdResponse;
	

	public ProductsResponse(Products products,  CategoriesResponse categoriesCategoryIdResponse,  SuppliersResponse suppliersSupplierIdResponse) {
								
		this.productId = products.getProductId();
		this.productName = products.getProductName();
		this.quantityPerUnit = products.getQuantityPerUnit();
		this.unitPrice = products.getUnitPrice();
		this.unitsInStock = products.getUnitsInStock();
		this.unitsOnOrder = products.getUnitsOnOrder();
		this.reorderLevel = products.getReorderLevel();
		this.discontinued = products.getDiscontinued();
		this.categoriesCategoryIdResponse =categoriesCategoryIdResponse;
		this.suppliersSupplierIdResponse =suppliersSupplierIdResponse;
		

	}
    
	public ProductsResponse(boolean isFlag, Products products) {
		if(isFlag){
			this.productId = products.getProductId();
			this.productName = products.getProductName();
			this.quantityPerUnit = products.getQuantityPerUnit();
			this.unitPrice = products.getUnitPrice();
			this.unitsInStock = products.getUnitsInStock();
			this.unitsOnOrder = products.getUnitsOnOrder();
			this.reorderLevel = products.getReorderLevel();
			this.discontinued = products.getDiscontinued();
			
		}
	}
  
}
