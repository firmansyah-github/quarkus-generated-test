// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.user.User;

import jakarta.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "USERS")
public class UserEntity {

  @Id private String id;

  private String username;
  private String bio;
  private String image;
  private String password;
  private String email;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")
  private List<ArticleEntity> articles;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  private List<FollowRelationshipEntity> following;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "followed")
  private List<FollowRelationshipEntity> followedBy;

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
