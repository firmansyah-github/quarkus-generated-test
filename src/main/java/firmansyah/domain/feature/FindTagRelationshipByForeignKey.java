// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import java.util.List;



public interface FindTagRelationshipByForeignKey {
  
	List<TagRelationship> handleForTagId(java.lang.String TagId);
	List<TagRelationship> handleForArticleId(java.lang.String ArticleId);
}

