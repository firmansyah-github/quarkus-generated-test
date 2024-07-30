// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class EmployeeTerritoriesEntityKey implements Serializable {

    @ManyToOne 
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private EmployeesEntity employeesEmployeeId;
    @ManyToOne 
    @JoinColumn(name = "territory_id", referencedColumnName = "territory_id")
    private TerritoriesEntity territoriesTerritoryId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	EmployeeTerritoriesEntityKey that = (EmployeeTerritoriesEntityKey) o;
    	return Objects.equals(employeesEmployeeId, that.employeesEmployeeId) && Objects.equals(territoriesTerritoryId, that.territoriesTerritoryId);
  	}

  	@Override
  	public int hashCode() {
    	return Objects.hash(employeesEmployeeId, territoriesTerritoryId);
  	}

}
