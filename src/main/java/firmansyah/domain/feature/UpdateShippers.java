// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.model.shippers.UpdateShippersInput;


public interface UpdateShippers {
	Shippers handle(UpdateShippersInput updateShippersInput);
}
