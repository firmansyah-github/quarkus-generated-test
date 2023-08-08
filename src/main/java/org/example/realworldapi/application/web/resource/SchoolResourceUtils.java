package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.school.School;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class SchoolResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ID");
		lsField.add("NAME");
	}

	
  
	public SchoolResponse schoolResponse(School school) {
		final var schoolResponse = new SchoolResponse(school );
		
        return schoolResponse;
	}

	public SchoolListResponse schoolResponse(PageResult<School> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(school -> schoolResponse(school))
				.collect(Collectors.toList());
		return new SchoolListResponse(resultResponse, pageResult.getTotal());
	}
	
	@Override
	protected String validateField(String string) {
		if(lsField.contains(string.trim().toUpperCase())) {
			return string.trim();
		} else {
			throw new FilterFieldNotValidException();
		}
	}
	
	@Override
	protected String transformToSqlField(String string) {
		switch (string) {
			case "id":
				return "id";
			case "name":
				return "name";
			default:
				return "";
		}
	}
}
