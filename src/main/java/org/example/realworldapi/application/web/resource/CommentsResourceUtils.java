package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.comments.Comments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class CommentsResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ID");
		lsField.add("BODY");
		lsField.add("CREATEDAT");
		lsField.add("UPDATEDAT");
		lsField.add("ARTICLEID");
		lsField.add("AUTHORID");
	}

	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	
  
	public CommentsResponse commentsResponse(Comments comments) {
		final var usersAuthorId =findUsersAuthorIdByPrimaryKey.handle(comments.getUsersAuthorId().getId());
		final var articlesArticleId =findArticlesArticleIdByPrimaryKey.handle(comments.getArticlesArticleId().getId());
		final var commentsResponse = new CommentsResponse(comments ,
										new UsersResponse(true, usersAuthorId),
										new ArticlesResponse(true, articlesArticleId) );
		
        return commentsResponse;
	}

	public CommentsListResponse commentsResponse(PageResult<Comments> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(comments -> commentsResponse(comments))
				.collect(Collectors.toList());
		return new CommentsListResponse(resultResponse, pageResult.getTotal());
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
			case "createdat":
				return "createdat";
			case "updatedat":
				return "updatedat";
			case "articleId":
				return "article_id";
			case "authorId":
				return "author_id";
			default:
				return "";
		}
	}
}
