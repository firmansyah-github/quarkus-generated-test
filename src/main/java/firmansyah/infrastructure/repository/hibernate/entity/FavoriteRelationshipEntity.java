// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "FavoriteRelationshipEntityFactor")
@Table(name = "FAVORITE_RELATIONSHIP")
public class FavoriteRelationshipEntity {

	@EmbeddedId 
	private FavoriteRelationshipEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "article_id", referencedColumnName = "id", insertable = false, updatable = false)
	private ArticlesEntity articlesArticleId;
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private UsersEntity usersUserId;

	public FavoriteRelationshipEntity(FavoriteRelationship favoriteRelationship ,UsersEntity usersUserIdEntity,ArticlesEntity articlesArticleIdEntity) {
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		this.primaryKey = favoriteRelationshipEntityKey;
		update(favoriteRelationship ,usersUserIdEntity,articlesArticleIdEntity);
		
  	}
  	
  	public void update(FavoriteRelationship favoriteRelationship ,UsersEntity usersUserIdEntity,ArticlesEntity articlesArticleIdEntity){
		this.articlesArticleId =articlesArticleIdEntity;
		this.usersUserId =usersUserIdEntity;
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	FavoriteRelationshipEntity that = (FavoriteRelationshipEntity) o;
    	return Objects.equals(primaryKey, that.primaryKey);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(articlesArticleId, usersUserId);
  	}
}
