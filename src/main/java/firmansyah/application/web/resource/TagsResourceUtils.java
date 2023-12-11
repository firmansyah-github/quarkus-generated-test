// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class TagsResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ID");
		lsField.add("NAME");
	}

	
  
	public TagsResponse tagsResponse(Tags tags) {
		final var tagsResponse = new TagsResponse(tags );
		
        return tagsResponse;
	}

	public TagsListResponse tagsResponse(PageResult<Tags> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(tags -> tagsResponse(tags))
				.collect(Collectors.toList());
		return new TagsListResponse(resultResponse, pageResult.getTotal());
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
