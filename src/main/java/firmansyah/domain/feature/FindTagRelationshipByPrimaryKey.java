// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
            






public interface FindTagRelationshipByPrimaryKey {
	TagRelationship handle(String articleId, String tagId);
}

