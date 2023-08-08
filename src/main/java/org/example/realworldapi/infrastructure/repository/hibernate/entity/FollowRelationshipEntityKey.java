package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FollowRelationshipEntityKey implements Serializable {

    @ManyToOne 
    @JoinColumn(name = "followed_id", referencedColumnName = "id")
    private UsersEntity usersFollowedId;
    @ManyToOne 
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UsersEntity usersUserId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	FollowRelationshipEntityKey that = (FollowRelationshipEntityKey) o;
    	return Objects.equals(usersFollowedId, that.usersFollowedId) && Objects.equals(usersUserId, that.usersUserId);
  	}

  	@Override
  	public int hashCode() {
    	return Objects.hash(usersFollowedId, usersUserId);
  	}

}
