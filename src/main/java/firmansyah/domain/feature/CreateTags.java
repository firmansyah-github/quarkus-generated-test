// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.NewTagsInput;

public interface CreateTags {
	Tags handle(NewTagsInput newTagsInput);
}
