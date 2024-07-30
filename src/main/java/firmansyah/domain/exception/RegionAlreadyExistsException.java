// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class RegionAlreadyExistsException extends BusinessException {

	public RegionAlreadyExistsException() {
		super(2, "region already exists");
	}
}
