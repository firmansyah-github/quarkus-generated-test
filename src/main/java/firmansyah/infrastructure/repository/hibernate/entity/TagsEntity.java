// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.tags.Tags;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TagsEntityFactor")
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
