// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.usStates.UsStates;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class UsStatesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("STATEID");
		lsField.add("STATENAME");
		lsField.add("STATEABBR");
		lsField.add("STATEREGION");
	}

	
  
	public UsStatesResponse usStatesResponse(UsStates usStates) {
		final var usStatesResponse = new UsStatesResponse(usStates );
		
        return usStatesResponse;
	}

	public UsStatesListResponse usStatesResponse(PageResult<UsStates> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(usStates -> usStatesResponse(usStates))
				.collect(Collectors.toList());
		return new UsStatesListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "stateId":
				return "stateId";
			
		    case "stateName":
				return "stateName";
			
		    case "stateAbbr":
				return "stateAbbr";
			
		    case "stateRegion":
				return "stateRegion";
			default:
				return "";
		}
	}
}
