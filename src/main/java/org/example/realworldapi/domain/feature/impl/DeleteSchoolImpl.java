package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteSchool;
import org.example.realworldapi.domain.model.school.SchoolRepository;


@AllArgsConstructor
public class DeleteSchoolImpl implements DeleteSchool {

	private final SchoolRepository schoolRepository;

  	@Override
	public boolean handle(String id) {
		return schoolRepository.delete(id);
	}
}
