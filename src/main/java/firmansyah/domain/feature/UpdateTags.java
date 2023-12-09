// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.UpdateTagsInput;


public interface UpdateTags {
	Tags handle(UpdateTagsInput updateTagsInput);
}
