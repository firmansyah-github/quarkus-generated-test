// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.UpdateTagRelationshipInput;


public interface UpdateTagRelationship {
	TagRelationship handle(UpdateTagRelationshipInput updateTagRelationshipInput);
}
