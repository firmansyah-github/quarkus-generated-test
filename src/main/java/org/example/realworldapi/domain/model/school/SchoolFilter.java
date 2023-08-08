package org.example.realworldapi.domain.model.school;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class SchoolFilter {
	private final int offset;
	private final int limit;
	private String id;
	private String name;
}
