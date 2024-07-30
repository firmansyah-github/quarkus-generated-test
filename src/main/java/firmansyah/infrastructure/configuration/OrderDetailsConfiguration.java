// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.orderDetails.OrderDetailsModelBuilder;
import firmansyah.domain.model.orderDetails.OrderDetailsRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class OrderDetailsConfiguration {

	@Produces
  	@Singleton
  	public CreateOrderDetails createOrderDetails(
		OrderDetailsRepository orderDetailsRepository,
      	OrderDetailsModelBuilder orderDetailsBuilder ,FindOrdersByPrimaryKey findOrdersOrderIdByPrimaryKey,FindProductsByPrimaryKey findProductsProductIdByPrimaryKey) {
    	return new CreateOrderDetailsImpl(
        	orderDetailsRepository,
        	orderDetailsBuilder ,findOrdersOrderIdByPrimaryKey,findProductsProductIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteOrderDetails deleteOrderDetails(
		OrderDetailsRepository orderDetailsRepository) {
    		return new DeleteOrderDetailsImpl(
        			orderDetailsRepository);
  	}

  	@Produces
  	@Singleton
  	public FindOrderDetailsByFilter findOrderDetailsByFilter(OrderDetailsRepository orderDetailsRepository) {
    	return new FindOrderDetailsByFilterImpl(orderDetailsRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindOrderDetailsByPrimaryKey findOrderDetailsByPrimaryKey(OrderDetailsRepository orderDetailsRepository) {
		return new FindOrderDetailsByPrimaryKeyImpl(orderDetailsRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateOrderDetails updateOrderDetails(
		OrderDetailsRepository orderDetailsRepository,
      	OrderDetailsModelBuilder orderDetailsBuilder,
      	FindOrderDetailsByPrimaryKey findOrderDetailsByPrimaryKey	,FindOrdersByPrimaryKey findOrdersOrderIdByPrimaryKey,FindProductsByPrimaryKey findProductsProductIdByPrimaryKey) {
		return new UpdateOrderDetailsImpl(
        	orderDetailsRepository,
        	orderDetailsBuilder,
        	findOrderDetailsByPrimaryKey ,findOrdersOrderIdByPrimaryKey,findProductsProductIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public OrderDetailsModelBuilder orderDetailsBuilder(ModelValidator modelValidator) {
		return new OrderDetailsModelBuilder(modelValidator);
  	}
}
