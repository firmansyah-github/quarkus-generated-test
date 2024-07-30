// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.region.Region;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class RegionResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("REGIONID");
		lsField.add("REGIONDESCRIPTION");
	}

	
  
	public RegionResponse regionResponse(Region region) {
		final var regionResponse = new RegionResponse(region );
		
        return regionResponse;
	}

	public RegionListResponse regionResponse(PageResult<Region> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(region -> regionResponse(region))
				.collect(Collectors.toList());
		return new RegionListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "regionId":
				return "regionId";
			
		    case "regionDescription":
				return "regionDescription";
			default:
				return "";
		}
	}
}
