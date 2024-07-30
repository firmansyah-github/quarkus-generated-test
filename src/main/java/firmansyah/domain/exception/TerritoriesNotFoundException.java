// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class TerritoriesNotFoundException extends BusinessException {

	public TerritoriesNotFoundException() {
		super(5, "territories not found");
	}
}
