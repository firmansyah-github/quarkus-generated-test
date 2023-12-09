// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.UpdateTagRelationshipInput;


public interface UpdateTagRelationship {
	TagRelationship handle(UpdateTagRelationshipInput updateTagRelationshipInput);
}
