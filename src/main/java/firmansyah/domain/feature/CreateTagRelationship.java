// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.NewTagRelationshipInput;

public interface CreateTagRelationship {
	TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput);
}
