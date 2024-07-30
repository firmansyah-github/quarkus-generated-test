// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class SuppliersNotFoundException extends BusinessException {

	public SuppliersNotFoundException() {
		super(5, "suppliers not found");
	}
}
