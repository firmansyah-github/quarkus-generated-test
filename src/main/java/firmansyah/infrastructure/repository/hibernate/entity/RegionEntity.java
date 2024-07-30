// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.region.Region;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "RegionEntityFactor")
@Table(name = "REGION")
public class RegionEntity {

	@Id
	@Column(name = "region_id")
	private Integer regionId;
	private String regionDescription;
	@OneToMany(mappedBy = "regionRegionId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TerritoriesEntity> territoriesRegionIdEntityList;

	public RegionEntity(Region region ) {
		this.regionId = region.getRegionId();
		update(region );
		
  	}
  	
  	public void update(Region region ){
		this.regionDescription = region.getRegionDescription();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	RegionEntity that = (RegionEntity) o;
    	return Objects.equals(regionId, that.regionId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(regionId);
  	}
}
