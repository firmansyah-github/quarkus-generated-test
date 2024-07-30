// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class EmployeeTerritoriesNotFoundException extends BusinessException {

	public EmployeeTerritoriesNotFoundException() {
		super(5, "employeeTerritories not found");
	}
}
