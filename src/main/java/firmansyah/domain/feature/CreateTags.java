// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.NewTagsInput;

public interface CreateTags {
	Tags handle(NewTagsInput newTagsInput);
}
