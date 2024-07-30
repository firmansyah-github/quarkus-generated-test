// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class FavoriteRelationshipResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ARTICLEID");
		lsField.add("USERID");
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
			
			case "articleId":
				return "articlesArticleId.id";	
			
			case "userId":
				return "usersUserId.id";	
			default:
				return "";
		}
	}
}
