// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.followRelationship.FollowRelationship;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FollowRelationshipEntityFactor")
@Table(name = "FOLLOW_RELATIONSHIP")
public class FollowRelationshipEntity {

	@EmbeddedId 
	private FollowRelationshipEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "followed_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UsersEntity usersFollowedId;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UsersEntity usersUserId;

	public FollowRelationshipEntity(FollowRelationship followRelationship ,UsersEntity usersFollowedIdEntity,UsersEntity usersUserIdEntity) {
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		this.primaryKey = followRelationshipEntityKey;
		update(followRelationship ,usersFollowedIdEntity,usersUserIdEntity);
		
  	}
  	
  	public void update(FollowRelationship followRelationship ,UsersEntity usersFollowedIdEntity,UsersEntity usersUserIdEntity){
		this.usersFollowedId =usersFollowedIdEntity;
		this.usersUserId =usersUserIdEntity;
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	FollowRelationshipEntity that = (FollowRelationshipEntity) o;
    	return Objects.equals(primaryKey, that.primaryKey);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(usersFollowedId, usersUserId);
  	}
}
