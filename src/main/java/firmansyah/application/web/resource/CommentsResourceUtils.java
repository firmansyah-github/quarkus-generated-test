// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class CommentsResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("CREATEDAT");
		lsField.add("UPDATEDAT");
		lsField.add("ARTICLEID");
		lsField.add("AUTHORID");
		lsField.add("BODY");
		lsField.add("ID");
	}

	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	private final FindUsersByPrimaryKey findUsersAuthorIdByPrimaryKey;
	
  
	public CommentsResponse commentsResponse(Comments comments) {
		final var articlesArticleId =findArticlesArticleIdByPrimaryKey.handle(comments.getArticlesArticleId().getId());
		final var usersAuthorId =findUsersAuthorIdByPrimaryKey.handle(comments.getUsersAuthorId().getId());
		final var commentsResponse = new CommentsResponse(comments ,
										new ArticlesResponse(true, articlesArticleId),
										new UsersResponse(true, usersAuthorId) );
		
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
			
		    case "createdat":
				return "createdat";
			
		    case "updatedat":
				return "updatedat";
			
			case "articleId":
				return "articlesArticleId.id";	
			
			case "authorId":
				return "usersAuthorId.id";	
			
		    case "body":
				return "body";
			
		    case "id":
				return "id";
			default:
				return "";
		}
	}
}
