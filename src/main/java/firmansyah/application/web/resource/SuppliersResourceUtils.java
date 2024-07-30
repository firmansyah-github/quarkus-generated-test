// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.suppliers.Suppliers;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class SuppliersResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("SUPPLIERID");
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
		lsField.add("HOMEPAGE");
	}

	
  
	public SuppliersResponse suppliersResponse(Suppliers suppliers) {
		final var suppliersResponse = new SuppliersResponse(suppliers );
		
        return suppliersResponse;
	}

	public SuppliersListResponse suppliersResponse(PageResult<Suppliers> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(suppliers -> suppliersResponse(suppliers))
				.collect(Collectors.toList());
		return new SuppliersListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "supplierId":
				return "supplierId";
			
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
			
		    case "homepage":
				return "homepage";
			default:
				return "";
		}
	}
}
