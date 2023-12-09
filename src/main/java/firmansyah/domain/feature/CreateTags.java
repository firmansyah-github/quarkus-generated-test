// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.NewTagsInput;

public interface CreateTags {
	Tags handle(NewTagsInput newTagsInput);
}
