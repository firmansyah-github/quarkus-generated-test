package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class TagRelationshipResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("TAGID");
		lsField.add("ARTICLEID");
	}

	private final FindTagsByPrimaryKey findTagsTagIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	
  
	public TagRelationshipResponse tagRelationshipResponse(TagRelationship tagRelationship) {
		final var tagsTagId =findTagsTagIdByPrimaryKey.handle(tagRelationship.getTagsTagId().getId());
		final var articlesArticleId =findArticlesArticleIdByPrimaryKey.handle(tagRelationship.getArticlesArticleId().getId());
		final var tagRelationshipResponse = new TagRelationshipResponse(tagRelationship ,
										new TagsResponse(true, tagsTagId),
										new ArticlesResponse(true, articlesArticleId) );
		
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
			case "tagId":
				return "tag_id";
			case "articleId":
				return "article_id";
			default:
				return "";
		}
	}
}
