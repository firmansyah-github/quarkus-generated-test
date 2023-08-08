package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.comments.Comments;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "COMMENTS")
public class CommentsEntity {

	@Id
	private String id;
	private String body;
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

	public CommentsEntity(Comments comments ,UsersEntity usersAuthorIdEntity,ArticlesEntity articlesArticleIdEntity) {
		this.id = comments.getId();
		update(comments ,usersAuthorIdEntity,articlesArticleIdEntity);
		
  	}
  	
  	public void update(Comments comments ,UsersEntity usersAuthorIdEntity,ArticlesEntity articlesArticleIdEntity){
		this.body = comments.getBody();
		this.createdat = comments.getCreatedat();
		this.updatedat = comments.getUpdatedat();
		this.articlesArticleId =articlesArticleIdEntity;
		this.usersAuthorId =usersAuthorIdEntity;
		
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
