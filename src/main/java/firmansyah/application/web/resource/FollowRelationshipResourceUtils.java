// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class FollowRelationshipResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("FOLLOWEDID");
		lsField.add("USERID");
	}

	private final FindUsersByPrimaryKey findUsersFollowedIdByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersUserIdByPrimaryKey;
	
  
	public FollowRelationshipResponse followRelationshipResponse(FollowRelationship followRelationship) {
		final var usersFollowedId =findUsersFollowedIdByPrimaryKey.handle(followRelationship.getUsersFollowedId().getId());
		final var usersUserId =findUsersUserIdByPrimaryKey.handle(followRelationship.getUsersUserId().getId());
		final var followRelationshipResponse = new FollowRelationshipResponse(followRelationship ,
										new UsersResponse(true, usersFollowedId),
										new UsersResponse(true, usersUserId) );
		
        return followRelationshipResponse;
	}

	public FollowRelationshipListResponse followRelationshipResponse(PageResult<FollowRelationship> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(followRelationship -> followRelationshipResponse(followRelationship))
				.collect(Collectors.toList());
		return new FollowRelationshipListResponse(resultResponse, pageResult.getTotal());
	}
	
	@Override
	protected String validateField(String string) {
		if(lsField.contains(string.trim().toUpperCase())) {
			return string.trim();
		} else {
			throw new FilterFieldNotValidException();
		}
	}
	
	@Override
	protected String transformToSqlField(String string) {
		switch (string) {
			
			case "followedId":
				return "usersFollowedId.id";	
			
			case "userId":
				return "usersUserId.id";	
			default:
				return "";
		}
	}
}
