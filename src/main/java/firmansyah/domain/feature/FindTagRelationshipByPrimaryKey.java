// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
            






public interface FindTagRelationshipByPrimaryKey {
	TagRelationship handle(String articleId, String tagId);
}

