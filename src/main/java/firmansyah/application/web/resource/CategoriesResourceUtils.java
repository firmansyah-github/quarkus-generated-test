// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class CategoriesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("CATEGORYID");
		lsField.add("CATEGORYNAME");
		lsField.add("DESCRIPTION");
		lsField.add("PICTURE");
	}

	
  
	public CategoriesResponse categoriesResponse(Categories categories) {
		final var categoriesResponse = new CategoriesResponse(categories );
		
        return categoriesResponse;
	}

	public CategoriesListResponse categoriesResponse(PageResult<Categories> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(categories -> categoriesResponse(categories))
				.collect(Collectors.toList());
		return new CategoriesListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "categoryId":
				return "categoryId";
			
		    case "categoryName":
				return "categoryName";
			
		    case "description":
				return "description";
			
		    case "picture":
				return "picture";
			default:
				return "";
		}
	}
}
