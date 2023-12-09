// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.exception;

public class TagsNotFoundException extends BusinessException {

	public TagsNotFoundException() {
		super(5, "tags not found");
	}
}
