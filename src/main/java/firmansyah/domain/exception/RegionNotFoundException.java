// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class RegionNotFoundException extends BusinessException {

	public RegionNotFoundException() {
		super(5, "region not found");
	}
}
