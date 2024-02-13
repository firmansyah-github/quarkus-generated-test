// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.exception;

public class TagsNotFoundException extends BusinessException {

	public TagsNotFoundException() {
		super(5, "tags not found");
	}
}
