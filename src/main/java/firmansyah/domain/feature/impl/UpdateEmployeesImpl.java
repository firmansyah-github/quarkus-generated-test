// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.employees.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateEmployeesImpl implements UpdateEmployees {

	private final EmployeesRepository employeesRepository;
	private final EmployeesModelBuilder employeesBuilder;
	private final FindEmployeesByPrimaryKey findEmployeesByPrimaryKey;
	private final FindEmployeesByPrimaryKey findEmployeesReportsToByPrimaryKey;
	

	@Override
	public Employees handle(UpdateEmployeesInput updateEmployeesInput) {
		var employees = findEmployeesByPrimaryKey.handle(updateEmployeesInput.getEmployeeId());
		
    	employees =
			employeesBuilder.build(updateEmployeesInput.getEmployeeId(),
					updateEmployeesInput.getLastName(),
					updateEmployeesInput.getFirstName(),
					updateEmployeesInput.getTitle(),
					updateEmployeesInput.getTitleOfCourtesy(),
					updateEmployeesInput.getBirthDate(),
					updateEmployeesInput.getHireDate(),
					updateEmployeesInput.getAddress(),
					updateEmployeesInput.getCity(),
					updateEmployeesInput.getRegion(),
					updateEmployeesInput.getPostalCode(),
					updateEmployeesInput.getCountry(),
					updateEmployeesInput.getHomePhone(),
					updateEmployeesInput.getExtension(),
					updateEmployeesInput.getPhoto(),
					updateEmployeesInput.getNotes(),
					updateEmployeesInput.getPhotoPath(),
					null,
					null,
					null,
					findEmployeesReportsToByPrimaryKey.handle(updateEmployeesInput.getReportsTo()));
		employeesRepository.update(employees);
    
		return employees;
	}
}
