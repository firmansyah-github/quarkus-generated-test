package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindSchoolByPrimaryKey;
import org.example.realworldapi.domain.exception.SchoolNotFoundException;
import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.domain.model.school.SchoolRepository;


@AllArgsConstructor
public class FindSchoolByPrimaryKeyImpl implements FindSchoolByPrimaryKey {

	private final SchoolRepository schoolRepository;

	@Override
	public School handle(String id) {
		return schoolRepository.findSchoolByPrimaryKey(id)
    			.orElseThrow(SchoolNotFoundException::new);
	}
}
