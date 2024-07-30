// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EmployeeTerritoriesEntityFactor")
@Table(name = "EMPLOYEE_TERRITORIES")
public class EmployeeTerritoriesEntity {

	@EmbeddedId 
	private EmployeeTerritoriesEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", insertable = false, updatable = false)
	private EmployeesEntity employeesEmployeeId;
	@ManyToOne
	@JoinColumn(name = "territory_id", referencedColumnName = "territory_id", insertable = false, updatable = false)
	private TerritoriesEntity territoriesTerritoryId;

	public EmployeeTerritoriesEntity(EmployeeTerritories employeeTerritories ,EmployeesEntity employeesEmployeeIdEntity,TerritoriesEntity territoriesTerritoryIdEntity) {
		final var employeeTerritoriesEntityKey = new EmployeeTerritoriesEntityKey();
		employeeTerritoriesEntityKey.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		employeeTerritoriesEntityKey.setTerritoriesTerritoryId(territoriesTerritoryIdEntity);
		this.primaryKey = employeeTerritoriesEntityKey;
		update(employeeTerritories ,employeesEmployeeIdEntity,territoriesTerritoryIdEntity);
		
  	}
  	
  	public void update(EmployeeTerritories employeeTerritories ,EmployeesEntity employeesEmployeeIdEntity,TerritoriesEntity territoriesTerritoryIdEntity){
		this.employeesEmployeeId =employeesEmployeeIdEntity;
		this.territoriesTerritoryId =territoriesTerritoryIdEntity;
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	EmployeeTerritoriesEntity that = (EmployeeTerritoriesEntity) o;
    	return Objects.equals(primaryKey, that.primaryKey);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(employeesEmployeeId, territoriesTerritoryId);
  	}
}
