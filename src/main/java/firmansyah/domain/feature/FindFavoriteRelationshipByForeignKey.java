// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import java.util.List;



public interface FindFavoriteRelationshipByForeignKey {
  
	List<FavoriteRelationship> handleForUserId(java.lang.String UserId);
	List<FavoriteRelationship> handleForArticleId(java.lang.String ArticleId);
}

