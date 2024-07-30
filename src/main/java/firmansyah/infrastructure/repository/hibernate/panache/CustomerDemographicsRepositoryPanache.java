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
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import firmansyah.domain.model.customerDemographics.CustomerDemographicsRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.CustomerDemographicsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.String;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CustomerDemographicsRepositoryPanache extends AbstractPanacheRepository<CustomerDemographicsEntity , String>
    implements CustomerDemographicsRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(CustomerDemographics customerDemographics) {
		persistAndFlush(new CustomerDemographicsEntity(customerDemographics ));
		
  	}

	@Override
	public Optional<CustomerDemographics> findCustomerDemographicsByPrimaryKey(String customerTypeId) {
		return findByIdOptional(customerTypeId).map(entityUtils::customerDemographics);
	}


  	@Override
  	public void update(CustomerDemographics customerDemographics) {
		final var customerDemographicsEntity = findById(customerDemographics.getCustomerTypeId());
		customerDemographicsEntity.update(customerDemographics );
		
    }

  
  	@Override
  	public boolean delete(String customerTypeId) {
		return deleteById(customerTypeId);
  	}

  	@Override
  	public PageResult<CustomerDemographics> findCustomerDemographicsByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<CustomerDemographicsEntity> queryBuilder = null;

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
        final var customerDemographicsResult = queryBuilder.list().stream()
        		                   .map(entityUtils::customerDemographics)
        		                   .collect(Collectors.toList());
  		
    	final var total = customerDemographicsResult.size();
    	return new PageResult<>(customerDemographicsResult, total);
  	}

  	@Override
  	public long countCustomerDemographics() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countCustomerDemographicsQueryBuilder = new SimpleQueryBuilder();
    	countCustomerDemographicsQueryBuilder.addQueryStatement("from CustomerDemographicsEntity as customerDemographics");
    
    	return count(countCustomerDemographicsQueryBuilder.toQueryString(), params);
  	}
}
