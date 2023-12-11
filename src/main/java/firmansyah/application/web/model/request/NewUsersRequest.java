// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import firmansyah.domain.model.users.NewUsersInput;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@JsonRootName("users")
@RegisterForReflection
public class NewUsersRequest {
  
	@Size(message = ValidationMessages.USERS_BIO_MAX_LENGTH, max = 255)
	private String bio;
	@Size(message = ValidationMessages.USERS_EMAIL_MAX_LENGTH, max = 255)
	private String email;
	@NotBlank(message = ValidationMessages.USERS_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.USERS_ID_MAX_LENGTH, max = 255)
	private String id;
	@Size(message = ValidationMessages.USERS_IMAGE_MAX_LENGTH, max = 255)
	private String image;
	@Size(message = ValidationMessages.USERS_PASSWORD_MAX_LENGTH, max = 255)
	private String password;
	@Size(message = ValidationMessages.USERS_USERNAME_MAX_LENGTH, max = 255)
	private String username;
  

	public NewUsersInput toNewUsersInput() {
		return new NewUsersInput(
			this.bio, this.email, this.id, this.image, this.password, this.username
		);
  	}

}
