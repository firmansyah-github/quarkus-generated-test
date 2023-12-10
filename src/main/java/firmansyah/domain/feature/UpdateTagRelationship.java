// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.UpdateTagRelationshipInput;


public interface UpdateTagRelationship {
	TagRelationship handle(UpdateTagRelationshipInput updateTagRelationshipInput);
}
