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
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemoRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerCustomerDemoEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import firmansyah.domain.feature.FindCustomerCustomerDemoByPrimaryKey;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerCustomerDemoEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.CustomersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerDemographicsEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CustomerCustomerDemoRepositoryPanache extends AbstractPanacheRepository<CustomerCustomerDemoEntity 		, FindCustomerCustomerDemoByPrimaryKey>
    implements CustomerCustomerDemoRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(CustomerCustomerDemo customerCustomerDemo) {
		final var customersCustomerIdEntity = getEntityManager().find(CustomersEntity.class,customerCustomerDemo.getCustomersCustomerId().getCustomerId());
		final var customerDemographicsCustomerTypeIdEntity = getEntityManager().find(CustomerDemographicsEntity.class,customerCustomerDemo.getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
		persistAndFlush(new CustomerCustomerDemoEntity(customerCustomerDemo ,customersCustomerIdEntity,customerDemographicsCustomerTypeIdEntity));
		
  	}

	@Override
	public Optional<CustomerCustomerDemo> findCustomerCustomerDemoByPrimaryKey(String customerId,String customerTypeId) {
		final var customerCustomerDemoEntityKey = new CustomerCustomerDemoEntityKey();
		customerCustomerDemoEntityKey.setCustomersCustomerId(getEntityManager().find(CustomersEntity.class,customerId));
		customerCustomerDemoEntityKey.setCustomerDemographicsCustomerTypeId(getEntityManager().find(CustomerDemographicsEntity.class,customerTypeId));
		return Optional.ofNullable(getEntityManager().find(CustomerCustomerDemoEntity.class,customerCustomerDemoEntityKey)).map(entityUtils::customerCustomerDemo);
		
	}


  	@Override
  	public void update(CustomerCustomerDemo customerCustomerDemo) {
		final var customersCustomerIdEntity= getEntityManager().find(CustomersEntity.class, customerCustomerDemo.getCustomersCustomerId().getCustomerId());
		final var customerDemographicsCustomerTypeIdEntity= getEntityManager().find(CustomerDemographicsEntity.class, customerCustomerDemo.getCustomerDemographicsCustomerTypeId().getCustomerTypeId());
		final var customerCustomerDemoEntityKey = new CustomerCustomerDemoEntityKey();
		customerCustomerDemoEntityKey.setCustomersCustomerId(customersCustomerIdEntity);
		customerCustomerDemoEntityKey.setCustomerDemographicsCustomerTypeId(customerDemographicsCustomerTypeIdEntity);
		final var customerCustomerDemoEntity = getEntityManager().find(CustomerCustomerDemoEntity.class, customerCustomerDemoEntityKey);
		customerCustomerDemoEntity.update(customerCustomerDemo ,customersCustomerIdEntity,customerDemographicsCustomerTypeIdEntity);
		
    }

  
  	@Override
  	public boolean delete(String customerId,String customerTypeId) {
		final var customersCustomerIdEntity=getEntityManager().find(CustomersEntity.class, customerId);
		final var customerDemographicsCustomerTypeIdEntity=getEntityManager().find(CustomerDemographicsEntity.class, customerTypeId);
		final var customerCustomerDemoEntityKey = new CustomerCustomerDemoEntityKey();
		customerCustomerDemoEntityKey.setCustomersCustomerId(customersCustomerIdEntity);
		customerCustomerDemoEntityKey.setCustomerDemographicsCustomerTypeId(customerDemographicsCustomerTypeIdEntity);
		
		
		final var customerCustomerDemoEntity = getEntityManager().find(CustomerCustomerDemoEntity.class, customerCustomerDemoEntityKey);
		try {
		     delete(customerCustomerDemoEntity);
			 return true;
		} catch (Exception e){
			 return false;
		}
  	}

  	@Override
  	public PageResult<CustomerCustomerDemo> findCustomerCustomerDemoByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<CustomerCustomerDemoEntity> queryBuilder = null;

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
        final var customerCustomerDemoResult = queryBuilder.list().stream()
        		                   .map(entityUtils::customerCustomerDemo)
        		                   .collect(Collectors.toList());
  		
    	final var total = customerCustomerDemoResult.size();
    	return new PageResult<>(customerCustomerDemoResult, total);
  	}

  	@Override
  	public long countCustomerCustomerDemo() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countCustomerCustomerDemoQueryBuilder = new SimpleQueryBuilder();
    	countCustomerCustomerDemoQueryBuilder.addQueryStatement("from CustomerCustomerDemoEntity as customerCustomerDemo");
    
    	return count(countCustomerCustomerDemoQueryBuilder.toQueryString(), params);
  	}
}
