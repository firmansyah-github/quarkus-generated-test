// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
            






public interface FindTagRelationshipByPrimaryKey {
	TagRelationship handle(String articleId, String tagId);
}
