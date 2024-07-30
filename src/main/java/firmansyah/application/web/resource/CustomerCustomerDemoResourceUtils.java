// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class CustomerCustomerDemoResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("CUSTOMERID");
		lsField.add("CUSTOMERTYPEID");
	}

	private final FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey;
	private final FindCustomerDemographicsByPrimaryKey findCustomerDemographicsCustomerTypeIdByPrimaryKey;
	
  
	public CustomerCustomerDemoResponse customerCustomerDemoResponse(CustomerCustomerDemo customerCustomerDemo) {
		final var customersCustomerId =findCustomersCustomerIdByPrimaryKey.handle(customerCustomerDemo.getCustomersCustomerId().getCustomerId());
		final var customerDemographicsCustomerTypeId =findCustomerDemographicsCustomerTypeIdByPrimaryKey.handle(customerCustomerDemo.getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
		final var customerCustomerDemoResponse = new CustomerCustomerDemoResponse(customerCustomerDemo ,
										new CustomersResponse(true, customersCustomerId),
										new CustomerDemographicsResponse(true, customerDemographicsCustomerTypeId) );
		
        return customerCustomerDemoResponse;
	}

	public CustomerCustomerDemoListResponse customerCustomerDemoResponse(PageResult<CustomerCustomerDemo> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(customerCustomerDemo -> customerCustomerDemoResponse(customerCustomerDemo))
				.collect(Collectors.toList());
		return new CustomerCustomerDemoListResponse(resultResponse, pageResult.getTotal());
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
				return "customersCustomerId.customerId";	
			
			case "customerTypeId":
				return "customerDemographicsCustomerTypeId.customerTypeId";	
			default:
				return "";
		}
	}
}
