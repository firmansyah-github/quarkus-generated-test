// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.users.Users;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "UsersEntityFactor")
@Table(name = "USERS")
public class UsersEntity {

	private String bio;
	private String email;
	@Id
	private String id;
	private String image;
	private String password;
	private String username;
	@OneToMany(mappedBy = "usersUserId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavoriteRelationshipEntity> favoriteRelationshipUserIdEntityList;
	@OneToMany(mappedBy = "usersFollowedId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FollowRelationshipEntity> followRelationshipFollowedIdEntityList;
	@OneToMany(mappedBy = "usersUserId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FollowRelationshipEntity> followRelationshipUserIdEntityList;
	@OneToMany(mappedBy = "usersAuthorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentsEntity> commentsAuthorIdEntityList;
	@OneToMany(mappedBy = "usersAuthorId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ArticlesEntity> articlesAuthorIdEntityList;

	public UsersEntity(Users users ) {
		this.id = users.getId();
		update(users );
		
  	}
  	
  	public void update(Users users ){
		this.bio = users.getBio();
		this.email = users.getEmail();
		this.image = users.getImage();
		this.password = users.getPassword();
		this.username = users.getUsername();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	UsersEntity that = (UsersEntity) o;
    	return Objects.equals(id, that.id);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(id);
  	}
}
