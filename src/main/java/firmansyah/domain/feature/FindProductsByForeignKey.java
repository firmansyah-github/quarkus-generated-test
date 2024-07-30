// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.products.Products;
import java.util.List;



public interface FindProductsByForeignKey {
  
	List<Products> handleForCategoryId(java.lang.Integer CategoryId);
	List<Products> handleForSupplierId(java.lang.Integer SupplierId);
}

