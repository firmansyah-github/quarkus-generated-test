package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindSchoolByFilter;
import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.school.SchoolRepository;
import org.example.realworldapi.domain.model.util.PageResult;

@AllArgsConstructor
public class FindSchoolByFilterImpl implements FindSchoolByFilter {

	private final SchoolRepository schoolRepository;

	@Override
	public PageResult<School> handle(ResourceFilter resourceFilterr) {
		return schoolRepository.findSchoolByFilter(resourceFilterr);
	}
}
