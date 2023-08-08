package org.example.realworldapi.domain.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewUsersInput {
	private String id;
	private String bio;
	private String email;
	private String image;
	private String password;
	private String username;
}
