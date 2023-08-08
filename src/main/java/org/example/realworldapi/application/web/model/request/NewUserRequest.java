package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.user.NewUserInput;
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@JsonRootName("user")
@RegisterForReflection
public class NewUserRequest {
  
	//@NotBlank(message = ValidationMessages.USER_ID_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.USER_ID_MAX_LENGTH, max = 2147483647)
	private String id; //java.lang.String
	@Size(message = ValidationMessages.USER_BIO_MAX_LENGTH, max = 255)
	private String bio; //java.lang.String
	@Size(message = ValidationMessages.USER_EMAIL_MAX_LENGTH, max = 255)
	@Email
	@NotBlank(message = ValidationMessages.EMAIL_MUST_BE_NOT_BLANK)
	private String email; //java.lang.String
	@Size(message = ValidationMessages.USER_IMAGE_MAX_LENGTH, max = 255)
	private String image; //java.lang.String
	@NotBlank(message = ValidationMessages.PASSWORD_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.USER_PASSWORD_MAX_LENGTH, max = 255)
	private String password; //java.lang.String
	@Size(message = ValidationMessages.USER_USERNAME_MAX_LENGTH, max = 255)
	@Pattern(regexp = "\\A(?!\\s*\\Z).+", message = ValidationMessages.USERNAME_MUST_BE_NOT_BLANK)
	@NotBlank(message = ValidationMessages.USERNAME_MUST_BE_NOT_BLANK)
	private String username; //java.lang.String
  

	public NewUserInput toNewUserInput() {
		return new NewUserInput(
			this.id,
			this.bio,
			this.email,
			this.image,
			this.password,
			this.username	);
  }
}

