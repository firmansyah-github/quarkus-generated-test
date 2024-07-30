// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class CustomerDemographicsResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("CUSTOMERTYPEID");
		lsField.add("CUSTOMERDESC");
	}

	
  
	public CustomerDemographicsResponse customerDemographicsResponse(CustomerDemographics customerDemographics) {
		final var customerDemographicsResponse = new CustomerDemographicsResponse(customerDemographics );
		
        return customerDemographicsResponse;
	}

	public CustomerDemographicsListResponse customerDemographicsResponse(PageResult<CustomerDemographics> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(customerDemographics -> customerDemographicsResponse(customerDemographics))
				.collect(Collectors.toList());
		return new CustomerDemographicsListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "customerTypeId":
				return "customerTypeId";
			
		    case "customerDesc":
				return "customerDesc";
			default:
				return "";
		}
	}
}
