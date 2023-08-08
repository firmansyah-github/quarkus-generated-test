package org.example.realworldapi.domain.model.followRelationship;

import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import org.example.realworldapi.domain.model.util.PageResult;


public interface FollowRelationshipRepository {

	void save(FollowRelationship followRelationship);

	Optional<FollowRelationship> findFollowRelationshipByPrimaryKey(String followedId,String userId);

	void update(FollowRelationship followRelationship);

	boolean delete(String followedId,String userId);

    PageResult<FollowRelationship> findFollowRelationshipByFilter(ResourceFilter resourceFilter);
    
	long countFollowRelationship();
}
