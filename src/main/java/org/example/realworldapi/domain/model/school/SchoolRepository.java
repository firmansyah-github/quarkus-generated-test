package org.example.realworldapi.domain.model.school;

import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import java.util.Optional;
import org.example.realworldapi.domain.model.util.PageResult;


public interface SchoolRepository {

	void save(School school);

	Optional<School> findSchoolByPrimaryKey(String id);

	void update(School school);

	boolean delete(String id);

    PageResult<School> findSchoolByFilter(ResourceFilter resourceFilter);
    
	long countSchool();
}
