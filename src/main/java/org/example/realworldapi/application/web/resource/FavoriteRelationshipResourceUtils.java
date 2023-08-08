package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class FavoriteRelationshipResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("USERID");
		lsField.add("ARTICLEID");
	}

	private final FindUsersByPrimaryKey findUsersUserIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	
  
	public FavoriteRelationshipResponse favoriteRelationshipResponse(FavoriteRelationship favoriteRelationship) {
		final var usersUserId =findUsersUserIdByPrimaryKey.handle(favoriteRelationship.getUsersUserId().getId());
		final var articlesArticleId =findArticlesArticleIdByPrimaryKey.handle(favoriteRelationship.getArticlesArticleId().getId());
		final var favoriteRelationshipResponse = new FavoriteRelationshipResponse(favoriteRelationship ,
										new UsersResponse(true, usersUserId),
										new ArticlesResponse(true, articlesArticleId) );
		
        return favoriteRelationshipResponse;
	}

	public FavoriteRelationshipListResponse favoriteRelationshipResponse(PageResult<FavoriteRelationship> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(favoriteRelationship -> favoriteRelationshipResponse(favoriteRelationship))
				.collect(Collectors.toList());
		return new FavoriteRelationshipListResponse(resultResponse, pageResult.getTotal());
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
			case "articleId":
				return "article_id";
			default:
				return "";
		}
	}
}
