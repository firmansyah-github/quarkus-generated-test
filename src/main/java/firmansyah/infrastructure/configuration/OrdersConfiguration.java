// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.configuration;

import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.impl.*;
import firmansyah.domain.model.orders.OrdersModelBuilder;
import firmansyah.domain.model.orders.OrdersRepository;
import firmansyah.domain.validator.ModelValidator;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

@Dependent
public class OrdersConfiguration {

	@Produces
  	@Singleton
  	public CreateOrders createOrders(
		OrdersRepository ordersRepository,
      	OrdersModelBuilder ordersBuilder ,FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey,FindShippersByPrimaryKey findShippersShipViaByPrimaryKey,FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey) {
    	return new CreateOrdersImpl(
        	ordersRepository,
        	ordersBuilder ,findEmployeesEmployeeIdByPrimaryKey,findShippersShipViaByPrimaryKey,findCustomersCustomerIdByPrimaryKey);
  	}
  
  	@Produces
  	@Singleton
  	public DeleteOrders deleteOrders(
		OrdersRepository ordersRepository) {
    		return new DeleteOrdersImpl(
        			ordersRepository);
  	}

  	@Produces
  	@Singleton
  	public FindOrdersByFilter findOrdersByFilter(OrdersRepository ordersRepository) {
    	return new FindOrdersByFilterImpl(ordersRepository);
  	}
  
  	@Produces
  	@Singleton
  	public FindOrdersByPrimaryKey findOrdersByPrimaryKey(OrdersRepository ordersRepository) {
		return new FindOrdersByPrimaryKeyImpl(ordersRepository);
  	}

  	@Produces
  	@Singleton
  	public UpdateOrders updateOrders(
		OrdersRepository ordersRepository,
      	OrdersModelBuilder ordersBuilder,
      	FindOrdersByPrimaryKey findOrdersByPrimaryKey	,FindEmployeesByPrimaryKey findEmployeesEmployeeIdByPrimaryKey,FindShippersByPrimaryKey findShippersShipViaByPrimaryKey,FindCustomersByPrimaryKey findCustomersCustomerIdByPrimaryKey) {
		return new UpdateOrdersImpl(
        	ordersRepository,
        	ordersBuilder,
        	findOrdersByPrimaryKey ,findEmployeesEmployeeIdByPrimaryKey,findShippersShipViaByPrimaryKey,findCustomersCustomerIdByPrimaryKey);
  	}
  

  	@Produces
  	@Singleton
  	public OrdersModelBuilder ordersBuilder(ModelValidator modelValidator) {
		return new OrdersModelBuilder(modelValidator);
  	}
}
