// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.exception;

public class TagsNotFoundException extends BusinessException {

	public TagsNotFoundException() {
		super(5, "tags not found");
	}
}
