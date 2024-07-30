// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.territories.Territories;
import firmansyah.domain.model.territories.NewTerritoriesInput;

public interface CreateTerritories {
	Territories handle(NewTerritoriesInput newTerritoriesInput);
}
