// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.exception;

import java.util.List;

public class ModelValidationException extends BusinessException {

	public ModelValidationException(List<String> messages) {
		super(8, messages);
	}
}
