// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

public class FilterFieldNotValidException extends BusinessException {

	public FilterFieldNotValidException() {
		super(6, "filter or sort field not valid");
	}
}
