package org.example.realworldapi.infrastructure.configuration;

import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.feature.impl.*;
import org.example.realworldapi.domain.model.school.SchoolModelBuilder;
import org.example.realworldapi.domain.model.school.SchoolRepository;
import org.example.realworldapi.domain.validator.ModelValidator;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Singleton;

@Dependent
public class SchoolConfiguration {

	@Produces
  	@Singleton
  	public CreateSchool createSchool(
		SchoolRepository schoolRepository,
      	SchoolModelBuilder schoolBuilder ) {
    	return new CreateSchoolImpl(
        	schoolRepository,
        	schoolBuilder );
  	}
  
  	@Produces
  	@Singleton
  	public DeleteSchool deleteSchool(
		SchoolRepository schoolRepository) {
    		return new DeleteSchoolImpl(
        			schoolRepository);
  	}

  	@Produces
  	@Singleton
  	public FindSchoolByFilter findSchoolByFilter(SchoolRepository schoolRepository) {
    	return new FindSchoolByFilterImpl(schoolRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindSchoolByPrimaryKey findSchoolByPrimaryKey(SchoolRepository schoolRepository) {
		return new FindSchoolByPrimaryKeyImpl(schoolRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateSchool updateSchool(
		SchoolRepository schoolRepository,
      	SchoolModelBuilder schoolBuilder,
      	FindSchoolByPrimaryKey findSchoolByPrimaryKey	) {
		return new UpdateSchoolImpl(
        	schoolRepository,
        	schoolBuilder,
        	findSchoolByPrimaryKey );
  	}
  

  	@Produces
  	@Singleton
  	public SchoolModelBuilder schoolBuilder(ModelValidator modelValidator) {
		return new SchoolModelBuilder(modelValidator);
  	}
}
