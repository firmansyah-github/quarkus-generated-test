// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.model.tagRelationship;

import firmansyah.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import firmansyah.domain.model.util.PageResult;


public interface TagRelationshipRepository {

	void save(TagRelationship tagRelationship);

	Optional<TagRelationship> findTagRelationshipByPrimaryKey(String articleId,String tagId);

	void update(TagRelationship tagRelationship);

	boolean delete(String articleId,String tagId);

    PageResult<TagRelationship> findTagRelationshipByFilter(ResourceFilter resourceFilter);
    
	long countTagRelationship();
}
