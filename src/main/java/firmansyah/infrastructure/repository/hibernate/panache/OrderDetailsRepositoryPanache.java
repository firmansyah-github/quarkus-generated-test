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
import firmansyah.domain.model.orderDetails.OrderDetails;
import firmansyah.domain.model.orderDetails.OrderDetailsRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.OrderDetailsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import firmansyah.domain.feature.FindOrderDetailsByPrimaryKey;
import firmansyah.infrastructure.repository.hibernate.entity.OrderDetailsEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.OrdersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.ProductsEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class OrderDetailsRepositoryPanache extends AbstractPanacheRepository<OrderDetailsEntity 		, FindOrderDetailsByPrimaryKey>
    implements OrderDetailsRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(OrderDetails orderDetails) {
		final var ordersOrderIdEntity = getEntityManager().find(OrdersEntity.class,orderDetails.getOrdersOrderId().getOrderId());
		final var productsProductIdEntity = getEntityManager().find(ProductsEntity.class,orderDetails.getProductsProductId().getProductId());
		persistAndFlush(new OrderDetailsEntity(orderDetails ,ordersOrderIdEntity,productsProductIdEntity));
		
  	}

	@Override
	public Optional<OrderDetails> findOrderDetailsByPrimaryKey(Integer orderId,Integer productId) {
		final var orderDetailsEntityKey = new OrderDetailsEntityKey();
		orderDetailsEntityKey.setOrdersOrderId(getEntityManager().find(OrdersEntity.class,orderId));
		orderDetailsEntityKey.setProductsProductId(getEntityManager().find(ProductsEntity.class,productId));
		return Optional.ofNullable(getEntityManager().find(OrderDetailsEntity.class,orderDetailsEntityKey)).map(entityUtils::orderDetails);
		
	}


  	@Override
  	public void update(OrderDetails orderDetails) {
		final var ordersOrderIdEntity= getEntityManager().find(OrdersEntity.class, orderDetails.getOrdersOrderId().getOrderId());
		final var productsProductIdEntity= getEntityManager().find(ProductsEntity.class, orderDetails.getProductsProductId().getProductId());
		final var orderDetailsEntityKey = new OrderDetailsEntityKey();
		orderDetailsEntityKey.setOrdersOrderId(ordersOrderIdEntity);
		orderDetailsEntityKey.setProductsProductId(productsProductIdEntity);
		final var orderDetailsEntity = getEntityManager().find(OrderDetailsEntity.class, orderDetailsEntityKey);
		orderDetailsEntity.update(orderDetails ,ordersOrderIdEntity,productsProductIdEntity);
		
    }

  
  	@Override
  	public boolean delete(Integer orderId,Integer productId) {
		final var ordersOrderIdEntity=getEntityManager().find(OrdersEntity.class, orderId);
		final var productsProductIdEntity=getEntityManager().find(ProductsEntity.class, productId);
		final var orderDetailsEntityKey = new OrderDetailsEntityKey();
		orderDetailsEntityKey.setOrdersOrderId(ordersOrderIdEntity);
		orderDetailsEntityKey.setProductsProductId(productsProductIdEntity);
		
		
		final var orderDetailsEntity = getEntityManager().find(OrderDetailsEntity.class, orderDetailsEntityKey);
		try {
		     delete(orderDetailsEntity);
			 return true;
		} catch (Exception e){
			 return false;
		}
  	}

  	@Override
  	public PageResult<OrderDetails> findOrderDetailsByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<OrderDetailsEntity> queryBuilder = null;

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
        final var orderDetailsResult = queryBuilder.list().stream()
        		                   .map(entityUtils::orderDetails)
        		                   .collect(Collectors.toList());
  		
    	final var total = orderDetailsResult.size();
    	return new PageResult<>(orderDetailsResult, total);
  	}

  	@Override
  	public long countOrderDetails() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countOrderDetailsQueryBuilder = new SimpleQueryBuilder();
    	countOrderDetailsQueryBuilder.addQueryStatement("from OrderDetailsEntity as orderDetails");
    
    	return count(countOrderDetailsQueryBuilder.toQueryString(), params);
  	}
}
