// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.exception;

public class TagsNotFoundException extends BusinessException {

	public TagsNotFoundException() {
		super(5, "tags not found");
	}
}
