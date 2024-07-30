// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateEmployees;
import firmansyah.domain.model.employees.*;
import firmansyah.domain.exception.EmployeesAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateEmployeesImpl implements CreateEmployees {

	private final EmployeesRepository employeesRepository;
	private final EmployeesModelBuilder employeesBuilder;
	private final FindEmployeesByPrimaryKey findEmployeesReportsToByPrimaryKey;
	

	@Override
	public Employees handle(NewEmployeesInput newEmployeesInput) {
		final var employees =
			employeesBuilder.build(newEmployeesInput.getEmployeeId(),
					newEmployeesInput.getLastName(),
					newEmployeesInput.getFirstName(),
					newEmployeesInput.getTitle(),
					newEmployeesInput.getTitleOfCourtesy(),
					newEmployeesInput.getBirthDate(),
					newEmployeesInput.getHireDate(),
					newEmployeesInput.getAddress(),
					newEmployeesInput.getCity(),
					newEmployeesInput.getRegion(),
					newEmployeesInput.getPostalCode(),
					newEmployeesInput.getCountry(),
					newEmployeesInput.getHomePhone(),
					newEmployeesInput.getExtension(),
					newEmployeesInput.getPhoto(),
					newEmployeesInput.getNotes(),
					newEmployeesInput.getPhotoPath(),
					null,
					null,
					null,
					findEmployeesReportsToByPrimaryKey.handle(newEmployeesInput.getReportsTo()));
		
		if(employeesRepository.findEmployeesByPrimaryKey(employees.getEmployeeId()).isPresent()) {
			throw new EmployeesAlreadyExistsException();
		} else {
			employeesRepository.save(employees);
		}
   
		return employees;
	}
}
