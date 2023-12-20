// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.model.favoriteRelationship;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface FavoriteRelationshipRepository {

	void save(FavoriteRelationship favoriteRelationship);

	Optional<FavoriteRelationship> findFavoriteRelationshipByPrimaryKey(String articleId,String userId);

	void update(FavoriteRelationship favoriteRelationship);

	boolean delete(String articleId,String userId);

    PageResult<FavoriteRelationship> findFavoriteRelationshipByFilter(ResourceFilter resourceFilter);
    
	long countFavoriteRelationship();
}
