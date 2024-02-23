// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.NewTagsInput;

public interface CreateTags {
	Tags handle(NewTagsInput newTagsInput);
}
