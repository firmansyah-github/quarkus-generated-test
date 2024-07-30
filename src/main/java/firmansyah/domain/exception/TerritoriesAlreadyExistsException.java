// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.exception;

public class TerritoriesAlreadyExistsException extends BusinessException {

	public TerritoriesAlreadyExistsException() {
		super(2, "territories already exists");
	}
}
