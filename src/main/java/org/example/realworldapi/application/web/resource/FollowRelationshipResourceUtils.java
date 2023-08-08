package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class FollowRelationshipResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("USERID");
		lsField.add("FOLLOWEDID");
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
			case "userId":
				return "user_id";
			case "followedId":
				return "followed_id";
			default:
				return "";
		}
	}
}
