// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.suppliers.Suppliers;
import firmansyah.domain.model.suppliers.NewSuppliersInput;

public interface CreateSuppliers {
	Suppliers handle(NewSuppliersInput newSuppliersInput);
}
