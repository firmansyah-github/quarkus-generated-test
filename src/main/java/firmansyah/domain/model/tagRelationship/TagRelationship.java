// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.tagRelationship;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.articles.Articles;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class TagRelationship {
	
	private Tags tagsTagId;
	private Articles articlesArticleId;
	
}
