package org.example.realworldapi.domain.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class TagsFilter {
	private final int offset;
	private final int limit;
	private String id;
	private String name;
}
