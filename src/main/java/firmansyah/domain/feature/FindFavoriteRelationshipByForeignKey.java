// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import java.util.List;



public interface FindFavoriteRelationshipByForeignKey {
  
	List<FavoriteRelationship> handleForUserId(java.lang.String UserId);
	List<FavoriteRelationship> handleForArticleId(java.lang.String ArticleId);
}

