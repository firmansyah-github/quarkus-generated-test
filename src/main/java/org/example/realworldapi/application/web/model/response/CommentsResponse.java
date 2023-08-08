package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.comments.Comments;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("comments")
@RegisterForReflection
public class CommentsResponse {

	@NotBlank(message = ValidationMessages.COMMENTS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.COMMENTS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.COMMENTS_BODY_MAX_LENGTH, max = 255)
	private String body;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime createdat;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime updatedat;
	private UsersResponse usersAuthorIdResponse;
	private ArticlesResponse articlesArticleIdResponse;
	

	public CommentsResponse(Comments comments,  UsersResponse usersAuthorIdResponse,  ArticlesResponse articlesArticleIdResponse) {
								
		this.id = comments.getId();
		this.body = comments.getBody();
		this.createdat = comments.getCreatedat();
		this.updatedat = comments.getUpdatedat();
		this.usersAuthorIdResponse =usersAuthorIdResponse;
		this.articlesArticleIdResponse =articlesArticleIdResponse;
		

	}
    
	public CommentsResponse(boolean isFlag, Comments comments) {
		if(isFlag){
			this.id = comments.getId();
			this.body = comments.getBody();
			this.createdat = comments.getCreatedat();
			this.updatedat = comments.getUpdatedat();
			
		}
	}
  
}
