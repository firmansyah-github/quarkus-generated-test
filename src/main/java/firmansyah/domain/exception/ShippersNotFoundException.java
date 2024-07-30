// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class ShippersNotFoundException extends BusinessException {

	public ShippersNotFoundException() {
		super(5, "shippers not found");
	}
}
