// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import firmansyah.application.web.resource.abs.FilterCondition;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.application.web.resource.abs.SortCondition;
import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UsersRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;
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
public class UsersRepositoryPanache extends AbstractPanacheRepository<UsersEntity , String>
    implements UsersRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Users users) {
		persistAndFlush(new UsersEntity(users ));
		
  	}

	@Override
	public Optional<Users> findUsersByPrimaryKey(String id) {
		return findByIdOptional(id).map(entityUtils::users);
	}


  	@Override
  	public void update(Users users) {
		final var usersEntity = findById(users.getId());
		usersEntity.update(users );
		
    }

  
  	@Override
  	public boolean delete(String id) {
		return deleteById(id);
  	}

  	@Override
  	public PageResult<Users> findUsersByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<UsersEntity> queryBuilder = null;

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
        
        if (queryCondition.toString().isEmpty() && sort == null) {
        	queryBuilder = findAll();
        }

        // Apply pagination
        queryBuilder.page(filter.getOffset(), filter.getLimit());

        // Execute the query
        final var usersResult = queryBuilder.list().stream()
        		                   .map(entityUtils::users)
        		                   .collect(Collectors.toList());
  		
    	final var total = usersResult.size();
    	return new PageResult<>(usersResult, total);
  	}

  	@Override
  	public long countUsers() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countUsersQueryBuilder = new SimpleQueryBuilder();
    	countUsersQueryBuilder.addQueryStatement("from UsersEntity as users");
    
    	return count(countUsersQueryBuilder.toQueryString(), params);
  	}
}
