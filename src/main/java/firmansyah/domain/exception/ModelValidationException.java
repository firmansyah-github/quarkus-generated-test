// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

import java.util.List;

public class ModelValidationException extends BusinessException {

	public ModelValidationException(List<String> messages) {
		super(8, messages);
	}
}
