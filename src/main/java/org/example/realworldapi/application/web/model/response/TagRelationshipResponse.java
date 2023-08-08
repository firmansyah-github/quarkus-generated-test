package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("tagRelationship")
@RegisterForReflection
public class TagRelationshipResponse {

	private TagsResponse tagsTagIdResponse;
	private ArticlesResponse articlesArticleIdResponse;
	

	public TagRelationshipResponse(TagRelationship tagRelationship,  TagsResponse tagsTagIdResponse,  ArticlesResponse articlesArticleIdResponse) {
								
		this.tagsTagIdResponse =tagsTagIdResponse;
		this.articlesArticleIdResponse =articlesArticleIdResponse;
		

	}
    
  
}
