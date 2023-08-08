package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.user.User;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "FGUSER")
public class UserEntity {

    @Id
	private UUID id;

	private String username;
	private String bio;
	private String image;
	private String password;
	private String email;

	public UserEntity(User user) {
		this.id = user.getId();
		update(user);
	}

	public void update(User user) {
		this.username = user.getUsername();
		this.bio = user.getBio();
		this.image = user.getImage();
		this.password = user.getPassword();
		this.email = user.getEmail();
	}
}
