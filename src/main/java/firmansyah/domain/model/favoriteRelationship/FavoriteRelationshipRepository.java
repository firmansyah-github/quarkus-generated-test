// created by the factor : Feb 23, 2024, 6:45:22 AM  
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
