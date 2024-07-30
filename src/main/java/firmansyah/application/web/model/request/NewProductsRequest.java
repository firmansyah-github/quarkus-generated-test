// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.products.NewProductsInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("products")
@RegisterForReflection
public class NewProductsRequest {
  
	@NotNull(message = ValidationMessages.PRODUCTS_PRODUCTID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer productId;
	@NotBlank(message = ValidationMessages.PRODUCTS_PRODUCTNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.PRODUCTS_PRODUCTNAME_MAX_LENGTH, max = 40)
	private String productName;
	@Max(5)
	private Integer supplierId;
	@Max(5)
	private Integer categoryId;
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
  

	public NewProductsInput toNewProductsInput() {
		return new NewProductsInput(
			this.productId, this.productName, this.supplierId, this.categoryId, this.quantityPerUnit, this.unitPrice, this.unitsInStock, this.unitsOnOrder, this.reorderLevel, this.discontinued
		);
  	}

}
