// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.UpdateTagsInput;


public interface UpdateTags {
	Tags handle(UpdateTagsInput updateTagsInput);
}
