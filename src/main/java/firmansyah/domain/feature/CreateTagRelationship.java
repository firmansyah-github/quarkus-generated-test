// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.NewTagRelationshipInput;

public interface CreateTagRelationship {
	TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput);
}
