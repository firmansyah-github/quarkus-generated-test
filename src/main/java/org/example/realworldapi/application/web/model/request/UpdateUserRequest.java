package org.example.realworldapi.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import org.example.realworldapi.domain.model.user.UpdateUserInput;
import org.example.realworldapi.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.UUID;


@Getter
@Setter
@JsonRootName("user")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateUserRequest {

  	//@NotNull(message = ValidationMessages.USER_ID_MUST_BE_NOT_BLANK)
	//@Size(message = ValidationMessages.USER_ID_MAX_LENGTH, max = 2147483647)
	private UUID id; //java.lang.String
	@Size(message = ValidationMessages.USER_BIO_MAX_LENGTH, max = 255)
	private String bio; //java.lang.String
	@Size(message = ValidationMessages.USER_EMAIL_MAX_LENGTH, max = 255)
	@Email
	private String email; //java.lang.String
	@Size(message = ValidationMessages.USER_IMAGE_MAX_LENGTH, max = 255)
	private String image; //java.lang.String
	@Size(message = ValidationMessages.USER_PASSWORD_MAX_LENGTH, max = 255)
	private String password; //java.lang.String
	@Size(message = ValidationMessages.USER_USERNAME_MAX_LENGTH, max = 255)
	@Pattern(regexp = "\\A(?!\\s*\\Z).+", message = ValidationMessages.USERNAME_MUST_BE_NOT_BLANK)
    private String username; //java.lang.String

	public UpdateUserInput toUpdateUserInput() {
		return new UpdateUserInput(
			this.id,
			this.bio,
			this.email,
			this.image,
			this.password,
			this.username		);
  	}
  	
  	public UpdateUserInput toUpdateUserInput(UUID userId) {
    	return new UpdateUserInput(userId,this.bio,
			this.email,
			this.image,
			this.password,this.username);
  	}
}
