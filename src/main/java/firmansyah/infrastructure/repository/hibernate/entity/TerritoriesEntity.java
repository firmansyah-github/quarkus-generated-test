// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.territories.Territories;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TerritoriesEntityFactor")
@Table(name = "TERRITORIES")
public class TerritoriesEntity {

	@Id
	@Column(name = "territory_id")
	private String territoryId;
	private String territoryDescription;
	@ManyToOne
	@JoinColumn(name = "region_id", referencedColumnName = "region_id", nullable = false)
	private RegionEntity regionRegionId;
	@OneToMany(mappedBy = "territoriesTerritoryId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeTerritoriesEntity> employeeTerritoriesTerritoryIdEntityList;

	public TerritoriesEntity(Territories territories ,RegionEntity regionRegionIdEntity) {
		this.territoryId = territories.getTerritoryId();
		update(territories ,regionRegionIdEntity);
		
  	}
  	
  	public void update(Territories territories ,RegionEntity regionRegionIdEntity){
		this.territoryDescription = territories.getTerritoryDescription();
		this.regionRegionId =regionRegionIdEntity;
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	TerritoriesEntity that = (TerritoriesEntity) o;
    	return Objects.equals(territoryId, that.territoryId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(territoryId);
  	}
}
