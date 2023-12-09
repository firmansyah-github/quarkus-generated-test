// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.articles.Articles;
import java.time.LocalDateTime;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ArticlesEntityFactor")
@Table(name = "ARTICLES")
public class ArticlesEntity {

	@CreationTimestamp
	private LocalDateTime createdat;
	@UpdateTimestamp
	private LocalDateTime updatedat;
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
	private UsersEntity usersAuthorId;
	private String body;
	private String description;
	@Id
	private String id;
	private String slug;
	private String title;
	@OneToMany(mappedBy = "articlesArticleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FavoriteRelationshipEntity> favoriteRelationshipArticleIdEntityList;
	@OneToMany(mappedBy = "articlesArticleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagRelationshipEntity> tagRelationshipArticleIdEntityList;
	@OneToMany(mappedBy = "articlesArticleId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CommentsEntity> commentsArticleIdEntityList;

	public ArticlesEntity(Articles articles ,UsersEntity usersAuthorIdEntity) {
		this.id = articles.getId();
		update(articles ,usersAuthorIdEntity);
		
  	}
  	
  	public void update(Articles articles ,UsersEntity usersAuthorIdEntity){
		this.createdat = articles.getCreatedat();
		this.updatedat = articles.getUpdatedat();
		this.usersAuthorId =usersAuthorIdEntity;
		this.body = articles.getBody();
		this.description = articles.getDescription();
		this.slug = articles.getSlug();
		this.title = articles.getTitle();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	ArticlesEntity that = (ArticlesEntity) o;
    	return Objects.equals(id, that.id);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(id);
  	}
}
