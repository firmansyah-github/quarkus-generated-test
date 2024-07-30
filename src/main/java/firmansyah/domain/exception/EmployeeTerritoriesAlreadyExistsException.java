// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class EmployeeTerritoriesAlreadyExistsException extends BusinessException {

	public EmployeeTerritoriesAlreadyExistsException() {
		super(2, "employeeterritories already exists");
	}
}
