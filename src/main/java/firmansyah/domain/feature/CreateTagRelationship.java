// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.NewTagRelationshipInput;

public interface CreateTagRelationship {
	TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput);
}
