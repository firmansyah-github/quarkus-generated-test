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
public class TagRelationshipEntityKey implements Serializable {

    @ManyToOne 
    @JoinColumn(name = "article_id", referencedColumnName = "id")
    private ArticlesEntity articlesArticleId;
    @ManyToOne 
    @JoinColumn(name = "tag_id", referencedColumnName = "id")
    private TagsEntity tagsTagId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	TagRelationshipEntityKey that = (TagRelationshipEntityKey) o;
    	return Objects.equals(articlesArticleId, that.articlesArticleId) && Objects.equals(tagsTagId, that.tagsTagId);
  	}

  	@Override
  	public int hashCode() {
    	return Objects.hash(articlesArticleId, tagsTagId);
  	}

}
