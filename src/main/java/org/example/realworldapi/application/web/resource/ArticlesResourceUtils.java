package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.articles.Articles;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class ArticlesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ID");
		lsField.add("BODY");
		lsField.add("DESCRIPTION");
		lsField.add("SLUG");
		lsField.add("TITLE");
		lsField.add("UPDATEDAT");
		lsField.add("AUTHORID");
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
			case "id":
				return "id";
			case "body":
				return "body";
			case "description":
				return "description";
			case "slug":
				return "slug";
			case "title":
				return "title";
			case "updatedat":
				return "updatedat";
			case "authorId":
				return "author_id";
			default:
				return "";
		}
	}
}