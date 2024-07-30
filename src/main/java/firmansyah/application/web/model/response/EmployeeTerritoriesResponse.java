// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("employeeTerritories")
@RegisterForReflection
public class EmployeeTerritoriesResponse {

	private EmployeesResponse employeesEmployeeIdResponse;
	private TerritoriesResponse territoriesTerritoryIdResponse;
	

	public EmployeeTerritoriesResponse(EmployeeTerritories employeeTerritories,  EmployeesResponse employeesEmployeeIdResponse,  TerritoriesResponse territoriesTerritoryIdResponse) {
								
		this.employeesEmployeeIdResponse =employeesEmployeeIdResponse;
		this.territoriesTerritoryIdResponse =territoriesTerritoryIdResponse;
		

	}
    
  
}
