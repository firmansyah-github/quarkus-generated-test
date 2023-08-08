package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindSchoolByFilter {
	PageResult<School> handle(ResourceFilter resourceFilter);
}