// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.employees.Employees;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class EmployeesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("EMPLOYEEID");
		lsField.add("LASTNAME");
		lsField.add("FIRSTNAME");
		lsField.add("TITLE");
		lsField.add("TITLEOFCOURTESY");
		lsField.add("BIRTHDATE");
		lsField.add("HIREDATE");
		lsField.add("ADDRESS");
		lsField.add("CITY");
		lsField.add("REGION");
		lsField.add("POSTALCODE");
		lsField.add("COUNTRY");
		lsField.add("HOMEPHONE");
		lsField.add("EXTENSION");
		lsField.add("PHOTO");
		lsField.add("NOTES");
		lsField.add("REPORTSTO");
		lsField.add("PHOTOPATH");
	}

	private final FindEmployeesByPrimaryKey findEmployeesReportsToByPrimaryKey;
	
  
	public EmployeesResponse employeesResponse(Employees employees) {
		final var employeesReportsTo =findEmployeesReportsToByPrimaryKey.handle(employees.getEmployeesReportsTo().getEmployeeId());
		final var employeesResponse = new EmployeesResponse(employees ,
										new EmployeesResponse(true, employeesReportsTo) );
		
        return employeesResponse;
	}

	public EmployeesListResponse employeesResponse(PageResult<Employees> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(employees -> employeesResponse(employees))
				.collect(Collectors.toList());
		return new EmployeesListResponse(resultResponse, pageResult.getTotal());
	}
	
	@Override
	protected String validateField(String string) {
		if(lsField.contains(string.trim().toUpperCase())) {
			return string.trim();
		} else {
			throw new FilterFieldNotValidException();
		}
	}
	
	@Override
	protected String transformToSqlField(String string) {
		switch (string) {
			
		    case "employeeId":
				return "employeeId";
			
		    case "lastName":
				return "lastName";
			
		    case "firstName":
				return "firstName";
			
		    case "title":
				return "title";
			
		    case "titleOfCourtesy":
				return "titleOfCourtesy";
			
		    case "birthDate":
				return "birthDate";
			
		    case "hireDate":
				return "hireDate";
			
		    case "address":
				return "address";
			
		    case "city":
				return "city";
			
		    case "region":
				return "region";
			
		    case "postalCode":
				return "postalCode";
			
		    case "country":
				return "country";
			
		    case "homePhone":
				return "homePhone";
			
		    case "extension":
				return "extension";
			
		    case "photo":
				return "photo";
			
		    case "notes":
				return "notes";
			
			case "reportsTo":
				return "employeesReportsTo.employeeId";	
			
		    case "photoPath":
				return "photoPath";
			default:
				return "";
		}
	}
}
