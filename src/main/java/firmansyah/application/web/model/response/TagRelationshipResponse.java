// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.tagRelationship.TagRelationship;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




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
