// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.exception;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

import static java.util.Collections.singletonList;

@Data
public class BusinessException extends RuntimeException {

    private final int code;
	private final List<String> messages;

	public BusinessException(int code, String message) {
		super(message);
		this.code = code;
		this.messages = new LinkedList<>(singletonList(message));
	}

	public BusinessException(int code, List<String> messages) {
		super(String.join(", ", messages));
		this.code = code;
		this.messages = messages;
	}
}
