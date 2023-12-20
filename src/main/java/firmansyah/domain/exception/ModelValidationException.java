// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

import java.util.List;

public class ModelValidationException extends BusinessException {

	public ModelValidationException(List<String> messages) {
		super(8, messages);
	}
}
