package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateSchool;
import org.example.realworldapi.domain.model.school.*;
import org.example.realworldapi.domain.exception.SchoolAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateSchoolImpl implements CreateSchool {

	private final SchoolRepository schoolRepository;
	private final SchoolModelBuilder schoolBuilder;
	

	@Override
	public School handle(NewSchoolInput newSchoolInput) {
		final var school =
			schoolBuilder.build(newSchoolInput.getId(),
					newSchoolInput.getName());
		
		if(schoolRepository.findSchoolByPrimaryKey(school.getId()).isPresent()) {
			throw new SchoolAlreadyExistsException();
		} else {
			schoolRepository.save(school);
		}
   
		return school;
	}
}
