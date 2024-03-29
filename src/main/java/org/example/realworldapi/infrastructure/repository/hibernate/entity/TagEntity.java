// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.realworldapi.domain.model.tag.Tag;

import jakarta.persistence.*;
import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "TAGS")
public class TagEntity {

  @Id private String id;

  private String name;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "tag")
  private List<TagRelationshipEntity> articlesTags;

  public TagEntity(Tag tag) {
    this.id = tag.getId();
    this.name = tag.getName();
  }
}
