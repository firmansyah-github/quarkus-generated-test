// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.usStates.UsStates;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UsStatesEntityFactor")
@Table(name = "US_STATES")
public class UsStatesEntity {

	@Id
	@Column(name = "state_id")
	private Integer stateId;
	private String stateName;
	private String stateAbbr;
	private String stateRegion;

	public UsStatesEntity(UsStates usStates ) {
		this.stateId = usStates.getStateId();
		update(usStates );
		
  	}
  	
  	public void update(UsStates usStates ){
		this.stateName = usStates.getStateName();
		this.stateAbbr = usStates.getStateAbbr();
		this.stateRegion = usStates.getStateRegion();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	UsStatesEntity that = (UsStatesEntity) o;
    	return Objects.equals(stateId, that.stateId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(stateId);
  	}
}
