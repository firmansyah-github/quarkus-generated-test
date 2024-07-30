// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.suppliers;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface SuppliersRepository {

	void save(Suppliers suppliers);

	Optional<Suppliers> findSuppliersByPrimaryKey(Integer supplierId);

	void update(Suppliers suppliers);

	boolean delete(Integer supplierId);

    PageResult<Suppliers> findSuppliersByFilter(ResourceFilter resourceFilter);
    
	long countSuppliers();
}
