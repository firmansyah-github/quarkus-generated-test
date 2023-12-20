// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.utils;

import jakarta.inject.Inject;

import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;

import java.util.UUID;

import firmansyah.infrastructure.web.provider.TokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

public class AbstractIntegrationTest extends DatabaseIntegrationTest {

	@Inject
	protected ObjectMapper objectMapper;
	@Inject
	protected TokenProvider tokenProvider;
	@Inject
	protected Slugify slugify;

	@AfterEach
	public void afterEach() {
		clear();
	}

	protected UsersEntity createUserEntity(String username, String email, String bio, String image, String password) {
		return transaction(() -> {
			final var userEntity = create(username, email, password, bio, image);
			entityManager.persist(userEntity);
			return userEntity;
		});
	}

	protected String token(UsersEntity userEntity) {
		return tokenProvider.createUserToken(userEntity.getId().toString());
	}

	public static UsersEntity create(String username, String email, String userPassword) {
		final var userEntity = new UsersEntity();
		userEntity.setId(UUID.randomUUID().toString());
		userEntity.setUsername(username);
		userEntity.setEmail(email);
		userEntity.setPassword(BCrypt.hashpw(userPassword, BCrypt.gensalt()));
		return userEntity;
	}

	public static UsersEntity create(String id, String username, String email, String userPassword) {
		final var userEntity = create(username, email, userPassword);
		userEntity.setId(id);
		return userEntity;
	}

	public static UsersEntity create(String username, String email, String userPassword, String bio, String image) {
		final var userEntity = create(username, email, userPassword);
		userEntity.setBio(bio);
		userEntity.setImage(image);
		return userEntity;
	}

}
