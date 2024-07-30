// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class OrdersResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ORDERID");
		lsField.add("CUSTOMERID");
		lsField.add("EMPLOYEEID");
		lsField.add("ORDERDATE");
		lsField.add("REQUIREDDATE");
		lsField.add("SHIPPEDDATE");
		lsField.add("SHIPVIA");
		lsField.add("FREIGHT");
		lsField.add("SHIPNAME");
		lsField.add("SHIPADDRESS");
		lsField.add("SHIPCITY");
		lsField.add("SHIPREGION");
		lsField.add("SHIPPOSTALCODE");
		lsField.add("SHIPCOUNTRY");
	}

	private final FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey;
	private final FindShippersByPrimaryKey findShippersShipViaByPrimaryKey;
	private final FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey;
	
  
	public OrdersResponse ordersResponse(Orders orders) {
		final var employeesEmployeeId =findEmployeesEmployeeIdByPrimaryKey.handle(orders.getEmployeesEmployeeId().getEmployeeId());
		final var shippersShipVia =findShippersShipViaByPrimaryKey.handle(orders.getShippersShipVia().getShipperId());
		final var customersCustomerId =findCustomersCustomerIdByPrimaryKey.handle(orders.getCustomersCustomerId().getCustomerId());
		final var ordersResponse = new OrdersResponse(orders ,
										new EmployeesResponse(true, employeesEmployeeId),
										new ShippersResponse(true, shippersShipVia),
										new CustomersResponse(true, customersCustomerId) );
		
        return ordersResponse;
	}

	public OrdersListResponse ordersResponse(PageResult<Orders> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(orders -> ordersResponse(orders))
				.collect(Collectors.toList());
		return new OrdersListResponse(resultResponse, pageResult.getTotal());
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
			
		    case "orderId":
				return "orderId";
			
			case "customerId":
				return "customersCustomerId.customerId";	
			
			case "employeeId":
				return "employeesEmployeeId.employeeId";	
			
		    case "orderDate":
				return "orderDate";
			
		    case "requiredDate":
				return "requiredDate";
			
		    case "shippedDate":
				return "shippedDate";
			
			case "shipVia":
				return "shippersShipVia.shipperId";	
			
		    case "freight":
				return "freight";
			
		    case "shipName":
				return "shipName";
			
		    case "shipAddress":
				return "shipAddress";
			
		    case "shipCity":
				return "shipCity";
			
		    case "shipRegion":
				return "shipRegion";
			
		    case "shipPostalCode":
				return "shipPostalCode";
			
		    case "shipCountry":
				return "shipCountry";
			default:
				return "";
		}
	}
}
