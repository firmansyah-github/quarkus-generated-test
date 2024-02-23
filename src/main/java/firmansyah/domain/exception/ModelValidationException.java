// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.exception;

import java.util.List;

public class ModelValidationException extends BusinessException {

	public ModelValidationException(List<String> messages) {
		super(8, messages);
	}
}
