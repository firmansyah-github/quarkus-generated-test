package org.example.realworldapi.utils;


import javax.inject.Inject;

import org.example.realworldapi.infrastructure.repository.hibernate.entity.UserEntity;
import org.example.realworldapi.infrastructure.web.provider.TokenProvider;
import org.junit.jupiter.api.AfterEach;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.slugify.Slugify;

public class AbstractIntegrationTest extends DatabaseIntegrationTest {

  @Inject protected ObjectMapper objectMapper;
  @Inject protected TokenProvider tokenProvider;
  @Inject protected Slugify slugify;

  @AfterEach
  public void afterEach() {
    clear();
  }

  protected UserEntity createUserEntity(
      String username, String email, String bio, String image, String password) {
    return transaction(
        () -> {
          final var userEntity = UserEntityUtils.create(username, email, password, bio, image);
          entityManager.persist(userEntity);
          return userEntity;
        });
  }

  protected String token(UserEntity userEntity) {
    return tokenProvider.createUserToken(userEntity.getId().toString());
  }

  

}
