// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import java.util.List;



public interface FindTagRelationshipByForeignKey {
  
	List<TagRelationship> handleForArticleId(java.lang.String ArticleId);
	List<TagRelationship> handleForTagId(java.lang.String TagId);
}

