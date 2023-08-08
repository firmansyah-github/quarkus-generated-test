package org.example.realworldapi.domain.model.user;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.realworldapi.domain.model.constants.ValidationMessages;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @NotNull(message = ValidationMessages.USER_ID_MUST_BE_NOT_BLANK)
	//@Size(message = ValidationMessages.USER_ID_MAX_LENGTH, max = 2147483647)
	private UUID id; //java.lang.String
	@Size(message = ValidationMessages.USER_USERNAME_MAX_LENGTH, max = 255)
	private String username; //java.lang.String
	@Email
	@Size(message = ValidationMessages.USER_EMAIL_MAX_LENGTH, max = 255)
	private String email; //java.lang.String
	@Size(message = ValidationMessages.USER_PASSWORD_MAX_LENGTH, max = 255)
	private String password; //java.lang.String
	
	@Size(message = ValidationMessages.USER_BIO_MAX_LENGTH, max = 255)
	private String bio; //java.lang.String
	
	@Size(message = ValidationMessages.USER_IMAGE_MAX_LENGTH, max = 255)
	private String image; //java.lang.String
	
	
	
}
