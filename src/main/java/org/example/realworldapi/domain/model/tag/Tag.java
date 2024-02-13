// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.model.tag;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Data
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {
  @NotNull private String id;
  @NotBlank private String name;
}
