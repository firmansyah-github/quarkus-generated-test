// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.model.user;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.example.realworldapi.domain.validator.ModelValidator;

import jakarta.inject.Named;


@Named
@AllArgsConstructor
public class UserModelBuilder {
  private final ModelValidator modelValidator;

  public User build(String username, String email, String password) {
    return modelValidator.validate(
        new User(UUID.randomUUID().toString(), username, email, password, null, null));
  }

  public User build(
      String id, String username, String bio, String image, String password, String email) {
    return modelValidator.validate(new User(id, username, email, password, bio, image));
  }
}
