// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
