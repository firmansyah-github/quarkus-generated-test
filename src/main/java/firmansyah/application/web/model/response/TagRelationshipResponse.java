// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.tagRelationship.TagRelationship;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("tagRelationship")
@RegisterForReflection
public class TagRelationshipResponse {

	private ArticlesResponse articlesArticleIdResponse;
	private TagsResponse tagsTagIdResponse;
	

	public TagRelationshipResponse(TagRelationship tagRelationship,  ArticlesResponse articlesArticleIdResponse,  TagsResponse tagsTagIdResponse) {
								
		this.articlesArticleIdResponse =articlesArticleIdResponse;
		this.tagsTagIdResponse =tagsTagIdResponse;
		

	}
    
  
}
