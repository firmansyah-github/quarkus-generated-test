// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import java.util.List;



public interface FindTagRelationshipByForeignKey {
  
	List<TagRelationship> handleForTagId(java.lang.String TagId);
	List<TagRelationship> handleForArticleId(java.lang.String ArticleId);
}

