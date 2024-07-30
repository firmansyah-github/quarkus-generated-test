// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.shippers.Shippers;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("shippers")
@RegisterForReflection
public class ShippersResponse {

	@NotNull(message = ValidationMessages.SHIPPERS_SHIPPERID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer shipperId;
	@NotBlank(message = ValidationMessages.SHIPPERS_COMPANYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.SHIPPERS_COMPANYNAME_MAX_LENGTH, max = 40)
	private String companyName;
	@Size(message = ValidationMessages.SHIPPERS_PHONE_MAX_LENGTH, max = 24)
	private String phone;
	

	public ShippersResponse(Shippers shippers) {
								
		this.shipperId = shippers.getShipperId();
		this.companyName = shippers.getCompanyName();
		this.phone = shippers.getPhone();
		

	}
    
	public ShippersResponse(boolean isFlag, Shippers shippers) {
		if(isFlag){
			this.shipperId = shippers.getShipperId();
			this.companyName = shippers.getCompanyName();
			this.phone = shippers.getPhone();
			
		}
	}
  
}
