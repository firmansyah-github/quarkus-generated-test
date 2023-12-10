// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

import java.util.List;

public class ModelValidationException extends BusinessException {

	public ModelValidationException(List<String> messages) {
		super(8, messages);
	}
}
