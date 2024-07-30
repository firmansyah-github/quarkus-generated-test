// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import java.util.List;



public interface FindFavoriteRelationshipByForeignKey {
  
	List<FavoriteRelationship> handleForUserId(java.lang.String UserId);
	List<FavoriteRelationship> handleForArticleId(java.lang.String ArticleId);
}

