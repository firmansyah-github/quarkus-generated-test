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
import firmansyah.domain.model.shippers.Shippers;
import firmansyah.domain.model.shippers.ShippersRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.ShippersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.Integer;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ShippersRepositoryPanache extends AbstractPanacheRepository<ShippersEntity , Integer>
    implements ShippersRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Shippers shippers) {
		persistAndFlush(new ShippersEntity(shippers ));
		
  	}

	@Override
	public Optional<Shippers> findShippersByPrimaryKey(Integer shipperId) {
		return findByIdOptional(shipperId).map(entityUtils::shippers);
	}


  	@Override
  	public void update(Shippers shippers) {
		final var shippersEntity = findById(shippers.getShipperId());
		shippersEntity.update(shippers );
		
    }

  
  	@Override
  	public boolean delete(Integer shipperId) {
		return deleteById(shipperId);
  	}

  	@Override
  	public PageResult<Shippers> findShippersByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<ShippersEntity> queryBuilder = null;

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
        final var shippersResult = queryBuilder.list().stream()
        		                   .map(entityUtils::shippers)
        		                   .collect(Collectors.toList());
  		
    	final var total = shippersResult.size();
    	return new PageResult<>(shippersResult, total);
  	}

  	@Override
  	public long countShippers() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countShippersQueryBuilder = new SimpleQueryBuilder();
    	countShippersQueryBuilder.addQueryStatement("from ShippersEntity as shippers");
    
    	return count(countShippersQueryBuilder.toQueryString(), params);
  	}
}
