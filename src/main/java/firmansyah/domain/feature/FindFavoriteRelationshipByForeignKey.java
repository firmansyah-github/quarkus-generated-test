// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import java.util.List;



public interface FindFavoriteRelationshipByForeignKey {
  
	List<FavoriteRelationship> handleForUserId(java.lang.String UserId);
	List<FavoriteRelationship> handleForArticleId(java.lang.String ArticleId);
}

