package org.example.realworldapi.application.web.resource;

import lombok.AllArgsConstructor;
import org.example.realworldapi.application.web.model.response.*;
import org.example.realworldapi.domain.exception.FilterFieldNotValidException;
import org.example.realworldapi.domain.feature.*;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.application.web.resource.abs.ResourceUtils;
import org.example.realworldapi.domain.model.users.Users;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class UsersResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ID");
		lsField.add("BIO");
		lsField.add("EMAIL");
		lsField.add("IMAGE");
		lsField.add("PASSWORD");
		lsField.add("USERNAME");
	}

	
  
	public UsersResponse usersResponse(Users users) {
		final var usersResponse = new UsersResponse(users );
		
        return usersResponse;
	}

	public UsersListResponse usersResponse(PageResult<Users> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(users -> usersResponse(users))
				.collect(Collectors.toList());
		return new UsersListResponse(resultResponse, pageResult.getTotal());
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
			case "bio":
				return "bio";
			case "email":
				return "email";
			case "image":
				return "image";
			case "password":
				return "password";
			case "username":
				return "username";
			default:
				return "";
		}
	}
}
