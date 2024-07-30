// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class TagRelationshipResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ARTICLEID");
		lsField.add("TAGID");
	}

	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	private final FindTagsByPrimaryKey findTagsTagIdByPrimaryKey;
	
  
	public TagRelationshipResponse tagRelationshipResponse(TagRelationship tagRelationship) {
		final var articlesArticleId =findArticlesArticleIdByPrimaryKey.handle(tagRelationship.getArticlesArticleId().getId());
		final var tagsTagId =findTagsTagIdByPrimaryKey.handle(tagRelationship.getTagsTagId().getId());
		final var tagRelationshipResponse = new TagRelationshipResponse(tagRelationship ,
										new ArticlesResponse(true, articlesArticleId),
										new TagsResponse(true, tagsTagId) );
		
        return tagRelationshipResponse;
	}

	public TagRelationshipListResponse tagRelationshipResponse(PageResult<TagRelationship> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(tagRelationship -> tagRelationshipResponse(tagRelationship))
				.collect(Collectors.toList());
		return new TagRelationshipListResponse(resultResponse, pageResult.getTotal());
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
			
			case "tagId":
				return "tagsTagId.id";	
			default:
				return "";
		}
	}
}
