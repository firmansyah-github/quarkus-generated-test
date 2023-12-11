// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import java.util.List;



public interface FindTagRelationshipByForeignKey {
  
	List<TagRelationship> handleForTagId(java.lang.String TagId);
	List<TagRelationship> handleForArticleId(java.lang.String ArticleId);
}

