package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FOLLOW_RELATIONSHIP")
public class FollowRelationshipEntity {

	@EmbeddedId 
	private FollowRelationshipEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UsersEntity usersUserId;
	@ManyToOne
	@JoinColumn(name = "followed_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UsersEntity usersFollowedId;

	public FollowRelationshipEntity(FollowRelationship followRelationship ,UsersEntity usersFollowedIdEntity,UsersEntity usersUserIdEntity) {
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		this.primaryKey = followRelationshipEntityKey;
		update(followRelationship ,usersFollowedIdEntity,usersUserIdEntity);
		
  	}
  	
  	public void update(FollowRelationship followRelationship ,UsersEntity usersFollowedIdEntity,UsersEntity usersUserIdEntity){
		this.usersUserId =usersUserIdEntity;
		this.usersFollowedId =usersFollowedIdEntity;
		
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
