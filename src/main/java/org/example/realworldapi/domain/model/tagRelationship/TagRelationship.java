package org.example.realworldapi.domain.model.tagRelationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;



import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.domain.model.articles.Articles;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TagRelationship {
	
	private Tags tagsTagId;
	private Articles articlesArticleId;
	
}
