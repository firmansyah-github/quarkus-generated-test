// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.comments.Comments;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("comments")
@RegisterForReflection
public class CommentsResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime createdat;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime updatedat;
	@Size(message = ValidationMessages.COMMENTS_BODY_MAX_LENGTH, max = 255)
	private String body;
	@NotBlank(message = ValidationMessages.COMMENTS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ID_MAX_LENGTH, max = 255)
	private String id;
	private ArticlesResponse articlesArticleIdResponse;
	private UsersResponse usersAuthorIdResponse;
	

	public CommentsResponse(Comments comments,  ArticlesResponse articlesArticleIdResponse,  UsersResponse usersAuthorIdResponse) {
								
		this.createdat = comments.getCreatedat();
		this.updatedat = comments.getUpdatedat();
		this.body = comments.getBody();
		this.id = comments.getId();
		this.articlesArticleIdResponse =articlesArticleIdResponse;
		this.usersAuthorIdResponse =usersAuthorIdResponse;
		

	}
    
	public CommentsResponse(boolean isFlag, Comments comments) {
		if(isFlag){
			this.createdat = comments.getCreatedat();
			this.updatedat = comments.getUpdatedat();
			this.body = comments.getBody();
			this.id = comments.getId();
			
		}
	}
  
}
