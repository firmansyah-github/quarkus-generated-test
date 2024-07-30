// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.customers.NewCustomersInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("customers")
@RegisterForReflection
public class NewCustomersRequest {
  
	@NotBlank(message = ValidationMessages.CUSTOMERS_CUSTOMERID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERS_CUSTOMERID_MAX_LENGTH, max = 5)
	private String customerId;
	@NotBlank(message = ValidationMessages.CUSTOMERS_COMPANYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CUSTOMERS_COMPANYNAME_MAX_LENGTH, max = 40)
	private String companyName;
	@Size(message = ValidationMessages.CUSTOMERS_CONTACTNAME_MAX_LENGTH, max = 30)
	private String contactName;
	@Size(message = ValidationMessages.CUSTOMERS_CONTACTTITLE_MAX_LENGTH, max = 30)
	private String contactTitle;
	@Size(message = ValidationMessages.CUSTOMERS_ADDRESS_MAX_LENGTH, max = 60)
	private String address;
	@Size(message = ValidationMessages.CUSTOMERS_CITY_MAX_LENGTH, max = 15)
	private String city;
	@Size(message = ValidationMessages.CUSTOMERS_REGION_MAX_LENGTH, max = 15)
	private String region;
	@Size(message = ValidationMessages.CUSTOMERS_POSTALCODE_MAX_LENGTH, max = 10)
	private String postalCode;
	@Size(message = ValidationMessages.CUSTOMERS_COUNTRY_MAX_LENGTH, max = 15)
	private String country;
	@Size(message = ValidationMessages.CUSTOMERS_PHONE_MAX_LENGTH, max = 24)
	private String phone;
	@Size(message = ValidationMessages.CUSTOMERS_FAX_MAX_LENGTH, max = 24)
	private String fax;
  

	public NewCustomersInput toNewCustomersInput() {
		return new NewCustomersInput(
			this.customerId, this.companyName, this.contactName, this.contactTitle, this.address, this.city, this.region, this.postalCode, this.country, this.phone, this.fax
		);
  	}

}
