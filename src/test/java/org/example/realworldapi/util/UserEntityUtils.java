// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.util;

import java.util.UUID;

import org.example.realworldapi.infrastructure.repository.hibernate.entity.UserEntity;
import org.mindrot.jbcrypt.BCrypt;



public class UserEntityUtils {

  public static UserEntity create(String username, String email, String userPassword) {
    final var userEntity = new UserEntity();
    userEntity.setId(UUID.randomUUID().toString());
    userEntity.setUsername(username);
    userEntity.setEmail(email);
    userEntity.setPassword(BCrypt.hashpw(userPassword, BCrypt.gensalt()));
    return userEntity;
  }

  public static UserEntity create(String id, String username, String email, String userPassword) {
    final var userEntity = create(username, email, userPassword);
    userEntity.setId(id);
    return userEntity;
  }

  public static UserEntity create(
      String username, String email, String userPassword, String bio, String image) {
    final var userEntity = create(username, email, userPassword);
    userEntity.setBio(bio);
    userEntity.setImage(image);
    return userEntity;
  }
}
