// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.user;

import java.util.Optional;


public interface UserRepository {
  void save(User user);

  boolean existsBy(String field, String value);

  Optional<User> findByEmail(String email);

  Optional<User> findUserById(String id);

  boolean existsUsername(String excludeId, String username);

  boolean existsEmail(String excludeId, String email);

  void update(User user);

  Optional<User> findByUsername(String username);
}
