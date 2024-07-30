// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.suppliers;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            


import firmansyah.domain.model.products.Products;
import java.util.List;
import java.util.stream.Collectors;

import java.util.UUID;

@AllArgsConstructor
public class SuppliersModelBuilder {

	private final ModelValidator modelValidator;

	public Suppliers build(Integer supplierId, String companyName, String contactName, String contactTitle, String address, String city, String region, String postalCode, String country, String phone, String fax, String homepage, List<Products> productsSupplierIdList) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Suppliers(supplierId, companyName, contactName, contactTitle, address, city, region, postalCode, country, phone, fax, homepage, productsSupplierIdList));
	}
  
}
