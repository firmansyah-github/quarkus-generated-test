// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.users.UpdateUsersInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("users")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateUsersRequest {

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

	public UpdateUsersInput toUpdateUsersInput() {
		return new UpdateUsersInput(
    		this.bio, this.email, this.id, this.image, this.password, this.username
		);
  }
}
