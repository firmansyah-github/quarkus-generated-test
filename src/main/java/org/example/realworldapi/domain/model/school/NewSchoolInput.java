package org.example.realworldapi.domain.model.school;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewSchoolInput {
	private String id;
	private String name;
}
