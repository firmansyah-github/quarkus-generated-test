// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.users.Users;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class UsersResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("BIO");
		lsField.add("EMAIL");
		lsField.add("ID");
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
			
		    case "bio":
				return "bio";
			
		    case "email":
				return "email";
			
		    case "id":
				return "id";
			
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
