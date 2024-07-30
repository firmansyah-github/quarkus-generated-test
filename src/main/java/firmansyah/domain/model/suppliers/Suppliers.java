// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.suppliers;

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
public class Suppliers {
	@NotNull(message = ValidationMessages.SUPPLIERS_SUPPLIERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer supplierId;
	@NotBlank(message = ValidationMessages.SUPPLIERS_COMPANYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SUPPLIERS_COMPANYNAME_MAX_LENGTH, max = 40)
	private String companyName;
	@Size(message = ValidationMessages.SUPPLIERS_CONTACTNAME_MAX_LENGTH, max = 30)
	private String contactName;
	@Size(message = ValidationMessages.SUPPLIERS_CONTACTTITLE_MAX_LENGTH, max = 30)
	private String contactTitle;
	@Size(message = ValidationMessages.SUPPLIERS_ADDRESS_MAX_LENGTH, max = 60)
	private String address;
	@Size(message = ValidationMessages.SUPPLIERS_CITY_MAX_LENGTH, max = 15)
	private String city;
	@Size(message = ValidationMessages.SUPPLIERS_REGION_MAX_LENGTH, max = 15)
	private String region;
	@Size(message = ValidationMessages.SUPPLIERS_POSTALCODE_MAX_LENGTH, max = 10)
	private String postalCode;
	@Size(message = ValidationMessages.SUPPLIERS_COUNTRY_MAX_LENGTH, max = 15)
	private String country;
	@Size(message = ValidationMessages.SUPPLIERS_PHONE_MAX_LENGTH, max = 24)
	private String phone;
	@Size(message = ValidationMessages.SUPPLIERS_FAX_MAX_LENGTH, max = 24)
	private String fax;
	@Size(message = ValidationMessages.SUPPLIERS_HOMEPAGE_MAX_LENGTH, max = 2147483647)
	private String homepage;
	private List<Products> productsSupplierIdList;
	
	
}
