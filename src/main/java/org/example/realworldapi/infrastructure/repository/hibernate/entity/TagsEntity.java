package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tags.Tags;
import java.util.List;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TAGS")
public class TagsEntity {

	@Id
	private String id;
	private String name;
	@OneToMany(mappedBy = "tagsTagId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<TagRelationshipEntity> tagRelationshipTagIdEntityList;

	public TagsEntity(Tags tags ) {
		this.id = tags.getId();
		update(tags );
		
  	}
  	
  	public void update(Tags tags ){
		this.name = tags.getName();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	TagsEntity that = (TagsEntity) o;
    	return Objects.equals(id, that.id);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(id);
  	}
}
