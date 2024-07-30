// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employees;

import lombok.AllArgsConstructor;

import firmansyah.domain.validator.ModelValidator;
            

import java.time.LocalDateTime;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.employees.Employees;
import java.util.List;
import java.util.stream.Collectors;

import firmansyah.domain.model.employees.Employees;
import java.util.UUID;

@AllArgsConstructor
public class EmployeesModelBuilder {

	private final ModelValidator modelValidator;

	public Employees build(Integer employeeId, String lastName, String firstName, String title, String titleOfCourtesy, LocalDateTime birthDate, LocalDateTime hireDate, String address, String city, String region, String postalCode, String country, String homePhone, String extension, byte[] photo, String notes, String photoPath, List<EmployeeTerritories> employeeTerritoriesEmployeeIdList, List<Orders> ordersEmployeeIdList, List<Employees> employeesReportsToList, Employees employeesReportsTo) {
		//final var createdAt = LocalDateTime.now();
		return modelValidator.validate(
			new Employees(employeeId, lastName, firstName, title, titleOfCourtesy, birthDate, hireDate, address, city, region, postalCode, country, homePhone, extension, photo, notes, photoPath, employeeTerritoriesEmployeeIdList, ordersEmployeeIdList, employeesReportsToList, employeesReportsTo));
	}
  
}
