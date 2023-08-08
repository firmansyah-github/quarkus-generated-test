package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TAG_RELATIONSHIP")
public class TagRelationshipEntity {

	@EmbeddedId 
	private TagRelationshipEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "tag_id", referencedColumnName = "id", insertable = false, updatable = false)
	private TagsEntity tagsTagId;
	@ManyToOne
	@JoinColumn(name = "article_id", referencedColumnName = "id", insertable = false, updatable = false)
	private ArticlesEntity articlesArticleId;

	public TagRelationshipEntity(TagRelationship tagRelationship ,TagsEntity tagsTagIdEntity,ArticlesEntity articlesArticleIdEntity) {
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		this.primaryKey = tagRelationshipEntityKey;
		update(tagRelationship ,tagsTagIdEntity,articlesArticleIdEntity);
		
  	}
  	
  	public void update(TagRelationship tagRelationship ,TagsEntity tagsTagIdEntity,ArticlesEntity articlesArticleIdEntity){
		this.tagsTagId =tagsTagIdEntity;
		this.articlesArticleId =articlesArticleIdEntity;
		
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
