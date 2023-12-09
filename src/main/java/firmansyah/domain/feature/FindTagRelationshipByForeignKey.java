// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import java.util.List;



public interface FindTagRelationshipByForeignKey {
  
	List<TagRelationship> handleForTagId(java.lang.String TagId);
	List<TagRelationship> handleForArticleId(java.lang.String ArticleId);
}

