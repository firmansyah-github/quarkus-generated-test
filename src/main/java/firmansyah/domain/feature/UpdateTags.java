// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.UpdateTagsInput;


public interface UpdateTags {
	Tags handle(UpdateTagsInput updateTagsInput);
}
