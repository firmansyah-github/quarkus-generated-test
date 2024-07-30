// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class EmployeeTerritoriesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("EMPLOYEEID");
		lsField.add("TERRITORYID");
	}

	private final FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey;
	private final FindTerritoriesByPrimaryKey findTerritoriesTerritoryIdByPrimaryKey;
	
  
	public EmployeeTerritoriesResponse employeeTerritoriesResponse(EmployeeTerritories employeeTerritories) {
		final var employeesEmployeeId =findEmployeesEmployeeIdByPrimaryKey.handle(employeeTerritories.getEmployeesEmployeeId().getEmployeeId());
		final var territoriesTerritoryId =findTerritoriesTerritoryIdByPrimaryKey.handle(employeeTerritories.getTerritoriesTerritoryId().getTerritoryId());
		final var employeeTerritoriesResponse = new EmployeeTerritoriesResponse(employeeTerritories ,
										new EmployeesResponse(true, employeesEmployeeId),
										new TerritoriesResponse(true, territoriesTerritoryId) );
		
        return employeeTerritoriesResponse;
	}

	public EmployeeTerritoriesListResponse employeeTerritoriesResponse(PageResult<EmployeeTerritories> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(employeeTerritories -> employeeTerritoriesResponse(employeeTerritories))
				.collect(Collectors.toList());
		return new EmployeeTerritoriesListResponse(resultResponse, pageResult.getTotal());
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
				return "employeesEmployeeId.employeeId";	
			
			case "territoryId":
				return "territoriesTerritoryId.territoryId";	
			default:
				return "";
		}
	}
}
