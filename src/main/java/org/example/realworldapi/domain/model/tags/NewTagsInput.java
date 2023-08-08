package org.example.realworldapi.domain.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewTagsInput {
	private String id;
	private String name;
}
