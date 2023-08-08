package org.example.realworldapi.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.model.constants.ValidationMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@JsonRootName("user")
@RegisterForReflection
public class UserResponse {
  
    @NotNull(message = ValidationMessages.USER_ID_MUST_BE_NOT_BLANK)
	//@Size(message = ValidationMessages.USER_ID_MAX_LENGTH, max = 2147483647)
	private UUID id; //java.lang.String
	@Size(message = ValidationMessages.USER_BIO_MAX_LENGTH, max = 255)
	private String bio; //java.lang.String
	@Size(message = ValidationMessages.USER_EMAIL_MAX_LENGTH, max = 255)
	private String email; //java.lang.String
	@Size(message = ValidationMessages.USER_IMAGE_MAX_LENGTH, max = 255)
	private String image; //java.lang.String
	@Size(message = ValidationMessages.USER_USERNAME_MAX_LENGTH, max = 255)
	private String username; //java.lang.String
	private String token;

	public UserResponse(
		User user
		) {
		this.id = user.getId();
		this.bio = user.getBio();
		this.email = user.getEmail();
		this.image = user.getImage();
		this.username = user.getUsername();
	}
  
	public UserResponse(boolean isFlag,
		User user
		) {
		this.id = user.getId();
		this.bio = user.getBio();
		this.email = user.getEmail();
		this.image = user.getImage();
		this.username = user.getUsername();
	}
	
	public UserResponse(User user, String token) {
	    this.id = user.getId();
		this.bio = user.getBio();
		this.email = user.getEmail();
		this.image = user.getImage();
		this.username = user.getUsername();
	    this.token = token;
  }
}
