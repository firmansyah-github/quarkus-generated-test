// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import java.util.List;



public interface FindFavoriteRelationshipByForeignKey {
  
	List<FavoriteRelationship> handleForUserId(java.lang.String UserId);
	List<FavoriteRelationship> handleForArticleId(java.lang.String ArticleId);
}

