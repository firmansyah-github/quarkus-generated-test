// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.territories.Territories;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class TerritoriesResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("TERRITORYID");
		lsField.add("TERRITORYDESCRIPTION");
		lsField.add("REGIONID");
	}

	private final FindRegionByPrimaryKey findRegionRegionIdByPrimaryKey;
	
  
	public TerritoriesResponse territoriesResponse(Territories territories) {
		final var regionRegionId =findRegionRegionIdByPrimaryKey.handle(territories.getRegionRegionId().getRegionId());
		final var territoriesResponse = new TerritoriesResponse(territories ,
										new RegionResponse(true, regionRegionId) );
		
        return territoriesResponse;
	}

	public TerritoriesListResponse territoriesResponse(PageResult<Territories> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(territories -> territoriesResponse(territories))
				.collect(Collectors.toList());
		return new TerritoriesListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "territoryId":
				return "territoryId";
			
		    case "territoryDescription":
				return "territoryDescription";
			
			case "regionId":
				return "regionRegionId.regionId";	
			default:
				return "";
		}
	}
}
