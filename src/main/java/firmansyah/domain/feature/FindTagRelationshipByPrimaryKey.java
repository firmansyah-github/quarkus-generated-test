// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
            






public interface FindTagRelationshipByPrimaryKey {
	TagRelationship handle(String articleId, String tagId);
}

