// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class ShippersResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("SHIPPERID");
		lsField.add("COMPANYNAME");
		lsField.add("PHONE");
	}

	
  
	public ShippersResponse shippersResponse(Shippers shippers) {
		final var shippersResponse = new ShippersResponse(shippers );
		
        return shippersResponse;
	}

	public ShippersListResponse shippersResponse(PageResult<Shippers> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(shippers -> shippersResponse(shippers))
				.collect(Collectors.toList());
		return new ShippersListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "shipperId":
				return "shipperId";
			
		    case "companyName":
				return "companyName";
			
		    case "phone":
				return "phone";
			default:
				return "";
		}
	}
}
