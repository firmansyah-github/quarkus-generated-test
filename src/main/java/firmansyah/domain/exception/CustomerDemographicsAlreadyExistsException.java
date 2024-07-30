// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class CustomerDemographicsAlreadyExistsException extends BusinessException {

	public CustomerDemographicsAlreadyExistsException() {
		super(2, "customerdemographics already exists");
	}
}
