// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavoriteRelationshipEntityKey implements Serializable {

    @ManyToOne 
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private ArticlesEntity articlesArticleId;
    @ManyToOne 
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UsersEntity usersUserId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	FavoriteRelationshipEntityKey that = (FavoriteRelationshipEntityKey) o;
    	return Objects.equals(articlesArticleId, that.articlesArticleId) && Objects.equals(usersUserId, that.usersUserId);
  	}

  	@Override
  	public int hashCode() {
    	return Objects.hash(articlesArticleId, usersUserId);
  	}

}
