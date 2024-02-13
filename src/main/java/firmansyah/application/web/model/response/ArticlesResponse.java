// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.articles.Articles;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("articles")
@RegisterForReflection
public class ArticlesResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime createdat;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime updatedat;
	@Size(message = ValidationMessages.ARTICLES_BODY_MAX_LENGTH, max = 255)
	private String body;
	@Size(message = ValidationMessages.ARTICLES_DESCRIPTION_MAX_LENGTH, max = 255)
	private String description;
	@NotBlank(message = ValidationMessages.ARTICLES_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.ARTICLES_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.ARTICLES_SLUG_MAX_LENGTH, max = 255)
	private String slug;
	@Size(message = ValidationMessages.ARTICLES_TITLE_MAX_LENGTH, max = 255)
	private String title;
	private UsersResponse usersAuthorIdResponse;
	

	public ArticlesResponse(Articles articles,  UsersResponse usersAuthorIdResponse) {
								
		this.createdat = articles.getCreatedat();
		this.updatedat = articles.getUpdatedat();
		this.body = articles.getBody();
		this.description = articles.getDescription();
		this.id = articles.getId();
		this.slug = articles.getSlug();
		this.title = articles.getTitle();
		this.usersAuthorIdResponse =usersAuthorIdResponse;
		

	}
    
	public ArticlesResponse(boolean isFlag, Articles articles) {
		if(isFlag){
			this.createdat = articles.getCreatedat();
			this.updatedat = articles.getUpdatedat();
			this.body = articles.getBody();
			this.description = articles.getDescription();
			this.id = articles.getId();
			this.slug = articles.getSlug();
			this.title = articles.getTitle();
			
		}
	}
  
}
