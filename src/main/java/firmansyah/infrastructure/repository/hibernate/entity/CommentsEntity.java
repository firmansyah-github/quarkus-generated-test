// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.comments.Comments;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CommentsEntityFactor")
@Table(name = "COMMENTS")
public class CommentsEntity {

	@CreationTimestamp
	private LocalDateTime createdat;
	@UpdateTimestamp
	private LocalDateTime updatedat;
	@ManyToOne
	@JoinColumn(name = "article_id", referencedColumnName = "id", nullable = false)
	private ArticlesEntity articlesArticleId;
	@ManyToOne
	@JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
	private UsersEntity usersAuthorId;
	private String body;
	@Id
	@Column(name = "id")
	private String id;

	public CommentsEntity(Comments comments ,ArticlesEntity articlesArticleIdEntity,UsersEntity usersAuthorIdEntity) {
		this.id = comments.getId();
		update(comments ,articlesArticleIdEntity,usersAuthorIdEntity);
		
  	}
  	
  	public void update(Comments comments ,ArticlesEntity articlesArticleIdEntity,UsersEntity usersAuthorIdEntity){
		this.createdat = comments.getCreatedat();
		this.updatedat = comments.getUpdatedat();
		this.articlesArticleId =articlesArticleIdEntity;
		this.usersAuthorId =usersAuthorIdEntity;
		this.body = comments.getBody();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	CommentsEntity that = (CommentsEntity) o;
    	return Objects.equals(id, that.id);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(id);
  	}
}
