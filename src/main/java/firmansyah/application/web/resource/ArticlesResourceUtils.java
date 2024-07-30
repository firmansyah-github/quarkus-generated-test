// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class ArticlesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("CREATEDAT");
		lsField.add("UPDATEDAT");
		lsField.add("AUTHORID");
		lsField.add("BODY");
		lsField.add("DESCRIPTION");
		lsField.add("ID");
		lsField.add("SLUG");
		lsField.add("TITLE");
	}

	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	
  
	public ArticlesResponse articlesResponse(Articles articles) {
		final var usersAuthorId =findUsersAuthorIdByPrimaryKey.handle(articles.getUsersAuthorId().getId());
		final var articlesResponse = new ArticlesResponse(articles ,
										new UsersResponse(true, usersAuthorId) );
		
        return articlesResponse;
	}

	public ArticlesListResponse articlesResponse(PageResult<Articles> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(articles -> articlesResponse(articles))
				.collect(Collectors.toList());
		return new ArticlesListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "createdat":
				return "createdat";
			
		    case "updatedat":
				return "updatedat";
			
			case "authorId":
				return "usersAuthorId.id";	
			
		    case "body":
				return "body";
			
		    case "description":
				return "description";
			
		    case "id":
				return "id";
			
		    case "slug":
				return "slug";
			
		    case "title":
				return "title";
			default:
				return "";
		}
	}
}
