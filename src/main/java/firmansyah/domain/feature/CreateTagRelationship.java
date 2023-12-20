// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.NewTagRelationshipInput;

public interface CreateTagRelationship {
	TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput);
}
