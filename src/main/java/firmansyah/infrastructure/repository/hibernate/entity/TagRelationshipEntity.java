// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.tagRelationship.TagRelationship;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TagRelationshipEntityFactor")
@Table(name = "TAG_RELATIONSHIP")
public class TagRelationshipEntity {

	@EmbeddedId 
	private TagRelationshipEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "article_id", referencedColumnName = "id", insertable = false, updatable = false)
	private ArticlesEntity articlesArticleId;
	@ManyToOne
	@JoinColumn(name = "tag_id", referencedColumnName = "id", insertable = false, updatable = false)
	private TagsEntity tagsTagId;

	public TagRelationshipEntity(TagRelationship tagRelationship ,ArticlesEntity articlesArticleIdEntity,TagsEntity tagsTagIdEntity) {
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		this.primaryKey = tagRelationshipEntityKey;
		update(tagRelationship ,articlesArticleIdEntity,tagsTagIdEntity);
		
  	}
  	
  	public void update(TagRelationship tagRelationship ,ArticlesEntity articlesArticleIdEntity,TagsEntity tagsTagIdEntity){
		this.articlesArticleId =articlesArticleIdEntity;
		this.tagsTagId =tagsTagIdEntity;
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	TagRelationshipEntity that = (TagRelationshipEntity) o;
    	return Objects.equals(primaryKey, that.primaryKey);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(articlesArticleId, tagsTagId);
  	}
}
