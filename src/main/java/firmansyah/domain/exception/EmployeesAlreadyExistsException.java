// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class EmployeesAlreadyExistsException extends BusinessException {

	public EmployeesAlreadyExistsException() {
		super(2, "employees already exists");
	}
}
