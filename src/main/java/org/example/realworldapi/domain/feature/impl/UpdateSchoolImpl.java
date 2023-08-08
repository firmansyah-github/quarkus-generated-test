package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.school.*;
import org.example.realworldapi.domain.feature.*;

@AllArgsConstructor
public class UpdateSchoolImpl implements UpdateSchool {

	private final SchoolRepository schoolRepository;
	private final SchoolModelBuilder schoolBuilder;
	private final FindSchoolByPrimaryKey findSchoolByPrimaryKey;
	

	@Override
	public School handle(UpdateSchoolInput updateSchoolInput) {
		var school = findSchoolByPrimaryKey.handle(updateSchoolInput.getId());
		
    	school =
			schoolBuilder.build(updateSchoolInput.getId(),
					updateSchoolInput.getName());
		schoolRepository.update(school);
    
		return school;
	}
}
