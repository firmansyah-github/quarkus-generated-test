// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.model.tag;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.validator.ModelValidator;
import java.util.UUID;



@AllArgsConstructor
public class TagBuilder {
  private final ModelValidator modelValidator;

  public Tag build(String name) {
    return modelValidator.validate(new Tag(UUID.randomUUID().toString(), name));
  }

  public Tag build(String id, String name) {
    return modelValidator.validate(new Tag(id, name));
  }
}
