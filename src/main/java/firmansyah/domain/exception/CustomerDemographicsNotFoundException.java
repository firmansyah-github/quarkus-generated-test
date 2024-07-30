// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CustomerDemographicsNotFoundException extends BusinessException {

	public CustomerDemographicsNotFoundException() {
		super(5, "customerDemographics not found");
	}
}
