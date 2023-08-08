package org.example.realworldapi.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NewUserInput {
  	private String id; //java.lang.String
	private String bio; //java.lang.String
	private String email; //java.lang.String
	private String image; //java.lang.String
	private String password; //java.lang.String
	private String username; //java.lang.String
}
