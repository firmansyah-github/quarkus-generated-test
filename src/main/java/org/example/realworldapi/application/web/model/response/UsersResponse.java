package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.users.Users;
            
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("users")
@RegisterForReflection
public class UsersResponse {

	@NotBlank(message = ValidationMessages.USERS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.USERS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.USERS_BIO_MAX_LENGTH, max = 255)
	private String bio;
	@Size(message = ValidationMessages.USERS_EMAIL_MAX_LENGTH, max = 255)
	private String email;
	@Size(message = ValidationMessages.USERS_IMAGE_MAX_LENGTH, max = 255)
	private String image;
	@Size(message = ValidationMessages.USERS_PASSWORD_MAX_LENGTH, max = 255)
	private String password;
	@Size(message = ValidationMessages.USERS_USERNAME_MAX_LENGTH, max = 255)
	private String username;
	

	public UsersResponse(Users users) {
								
		this.id = users.getId();
		this.bio = users.getBio();
		this.email = users.getEmail();
		this.image = users.getImage();
		this.password = users.getPassword();
		this.username = users.getUsername();
		

	}
    
	public UsersResponse(boolean isFlag, Users users) {
		if(isFlag){
			this.id = users.getId();
			this.bio = users.getBio();
			this.email = users.getEmail();
			this.image = users.getImage();
			this.password = users.getPassword();
			this.username = users.getUsername();
			
		}
	}
  
}
