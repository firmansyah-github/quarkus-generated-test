// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import firmansyah.application.web.resource.abs.FilterCondition;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.application.web.resource.abs.SortCondition;
import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.orders.OrdersRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.OrdersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.Integer;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.ShippersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CustomersEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OrdersRepositoryPanache extends AbstractPanacheRepository<OrdersEntity , Integer>
    implements OrdersRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Orders orders) {
		final var employeesEmployeeIdEntity = getEntityManager().find(EmployeesEntity.class,orders.getEmployeesEmployeeId().getEmployeeId());
		final var shippersShipViaEntity = getEntityManager().find(ShippersEntity.class,orders.getShippersShipVia().getShipperId());
		final var customersCustomerIdEntity = getEntityManager().find(CustomersEntity.class,orders.getCustomersCustomerId().getCustomerId());
		persistAndFlush(new OrdersEntity(orders ,employeesEmployeeIdEntity,shippersShipViaEntity,customersCustomerIdEntity));
		
  	}

	@Override
	public Optional<Orders> findOrdersByPrimaryKey(Integer orderId) {
		return findByIdOptional(orderId).map(entityUtils::orders);
	}


  	@Override
  	public void update(Orders orders) {
		final var employeesEmployeeIdEntity= getEntityManager().find(EmployeesEntity.class, orders.getEmployeesEmployeeId().getEmployeeId());
		final var shippersShipViaEntity= getEntityManager().find(ShippersEntity.class, orders.getShippersShipVia().getShipperId());
		final var customersCustomerIdEntity= getEntityManager().find(CustomersEntity.class, orders.getCustomersCustomerId().getCustomerId());
		final var ordersEntity = findById(orders.getOrderId());
		ordersEntity.update(orders ,employeesEmployeeIdEntity,shippersShipViaEntity,customersCustomerIdEntity);
		
    }

  
  	@Override
  	public boolean delete(Integer orderId) {
		return deleteById(orderId);
  	}

  	@Override
  	public PageResult<Orders> findOrdersByFilter(ResourceFilter filter) {
    	// Create a Parameters object
        Parameters params = new Parameters();

        // Build the query condition string and parameterize the fields
        StringBuilder queryCondition = new StringBuilder();
        int fieldIndex = 1;
        for (FilterCondition field : filter.getFilterConditions()) {
            String paramName = "value" + fieldIndex;
            queryCondition.append(field.getFieldSQL()).append(" ").append(field.getOperatorSQL())
                         .append(" :").append(paramName).append(" ")
                         .append(field.getConjunction().toString()).append(" ");
            if(field.getValue().matches("^(\\d{4}-\\d{2}-\\d{2})T(\\d{2}:\\d{2}:\\d{2}.\\d{6})$")) {
            	LocalDateTime dateTime = LocalDateTime.parse(field.getValue());
            	params.and(paramName, dateTime);
            } else {
            	params.and(paramName, field.getValue());
            }
            fieldIndex++;
        }
        
        PanacheQuery<OrdersEntity> queryBuilder = null;

        // Apply sorting
        Sort sort = null;
        for (SortCondition field : filter.getSortConditions()) {
            String sortField = field.getFieldSQL();
            Sort.Direction sortDirection = field.isDescending()?Sort.Direction.Descending:Sort.Direction.Ascending;
            if (sort == null) {
                sort = Sort.by(sortField, sortDirection);
            } else {
                sort.and(sortField, sortDirection);
            }
        }
        if (!queryCondition.toString().isEmpty() && sort != null) {
            queryBuilder = find(queryCondition.toString(), sort, params);
        }
        
        if (!queryCondition.toString().isEmpty() && sort == null) {
        	queryBuilder = find(queryCondition.toString(), params);
        } 
        
        if (queryCondition.toString().isEmpty() && sort != null) {
            queryBuilder = findAll(sort);
        }
        
        if (queryBuilder == null && queryCondition.toString().isEmpty() && sort == null) {
        	queryBuilder = findAll();
        }

        // Apply pagination
        //queryBuilder.page(filter.getOffset(), filter.getLimit());
        queryBuilder.range(filter.getOffset(), filter.getOffset()+filter.getLimit()-1);

        // Execute the query
        final var ordersResult = queryBuilder.list().stream()
        		                   .map(entityUtils::orders)
        		                   .collect(Collectors.toList());
  		
    	final var total = ordersResult.size();
    	return new PageResult<>(ordersResult, total);
  	}

  	@Override
  	public long countOrders() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countOrdersQueryBuilder = new SimpleQueryBuilder();
    	countOrdersQueryBuilder.addQueryStatement("from OrdersEntity as orders");
    
    	return count(countOrdersQueryBuilder.toQueryString(), params);
  	}
}
