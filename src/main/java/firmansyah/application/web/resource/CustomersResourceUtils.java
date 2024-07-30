// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.customers.Customers;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class CustomersResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("CUSTOMERID");
		lsField.add("COMPANYNAME");
		lsField.add("CONTACTNAME");
		lsField.add("CONTACTTITLE");
		lsField.add("ADDRESS");
		lsField.add("CITY");
		lsField.add("REGION");
		lsField.add("POSTALCODE");
		lsField.add("COUNTRY");
		lsField.add("PHONE");
		lsField.add("FAX");
	}

	
  
	public CustomersResponse customersResponse(Customers customers) {
		final var customersResponse = new CustomersResponse(customers );
		
        return customersResponse;
	}

	public CustomersListResponse customersResponse(PageResult<Customers> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(customers -> customersResponse(customers))
				.collect(Collectors.toList());
		return new CustomersListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "customerId":
				return "customerId";
			
		    case "companyName":
				return "companyName";
			
		    case "contactName":
				return "contactName";
			
		    case "contactTitle":
				return "contactTitle";
			
		    case "address":
				return "address";
			
		    case "city":
				return "city";
			
		    case "region":
				return "region";
			
		    case "postalCode":
				return "postalCode";
			
		    case "country":
				return "country";
			
		    case "phone":
				return "phone";
			
		    case "fax":
				return "fax";
			default:
				return "";
		}
	}
}
