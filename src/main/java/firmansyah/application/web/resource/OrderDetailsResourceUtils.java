// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.resource;

import lombok.AllArgsConstructor;

import firmansyah.application.web.model.response.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.model.util.PageResult;

import firmansyah.application.web.resource.abs.ResourceUtils;
import firmansyah.domain.model.orderDetails.OrderDetails;
import firmansyah.domain.exception.FilterFieldNotValidException;
import firmansyah.domain.feature.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import jakarta.enterprise.context.ApplicationScoped;



@ApplicationScoped
@AllArgsConstructor
public class OrderDetailsResourceUtils extends ResourceUtils{

    private static List<String> lsField = new ArrayList<String>();
	
	static {
		lsField.add("ORDERID");
		lsField.add("PRODUCTID");
		lsField.add("UNITPRICE");
		lsField.add("QUANTITY");
		lsField.add("DISCOUNT");
	}

	private final FindOrdersByPrimaryKey findOrdersOrderIdByPrimaryKey;
	private final FindProductsByPrimaryKey findProductsProductIdByPrimaryKey;
	
  
	public OrderDetailsResponse orderDetailsResponse(OrderDetails orderDetails) {
		final var ordersOrderId =findOrdersOrderIdByPrimaryKey.handle(orderDetails.getOrdersOrderId().getOrderId());
		final var productsProductId =findProductsProductIdByPrimaryKey.handle(orderDetails.getProductsProductId().getProductId());
		final var orderDetailsResponse = new OrderDetailsResponse(orderDetails ,
										new OrdersResponse(true, ordersOrderId),
										new ProductsResponse(true, productsProductId) );
		
        return orderDetailsResponse;
	}

	public OrderDetailsListResponse orderDetailsResponse(PageResult<OrderDetails> pageResult) {
		final var resultResponse =
			pageResult.getResult().stream()
				.map(orderDetails -> orderDetailsResponse(orderDetails))
				.collect(Collectors.toList());
		return new OrderDetailsListResponse(resultResponse, pageResult.getTotal());
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
				return "ordersOrderId.orderId";	
			
			case "productId":
				return "productsProductId.productId";	
			
		    case "unitPrice":
				return "unitPrice";
			
		    case "quantity":
				return "quantity";
			
		    case "discount":
				return "discount";
			default:
				return "";
		}
	}
}
