// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class EmployeesNotFoundException extends BusinessException {

	public EmployeesNotFoundException() {
		super(5, "employees not found");
	}
}
