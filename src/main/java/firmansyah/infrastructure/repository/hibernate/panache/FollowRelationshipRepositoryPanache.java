// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import firmansyah.application.web.resource.abs.FilterCondition;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.application.web.resource.abs.SortCondition;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationshipRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.FollowRelationshipEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import firmansyah.domain.feature.FindFollowRelationshipByPrimaryKey;
import firmansyah.infrastructure.repository.hibernate.entity.FollowRelationshipEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class FollowRelationshipRepositoryPanache extends AbstractPanacheRepository<FollowRelationshipEntity 		, FindFollowRelationshipByPrimaryKey>
    implements FollowRelationshipRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(FollowRelationship followRelationship) {
		final var usersFollowedIdEntity = getEntityManager().find(UsersEntity.class,followRelationship.getUsersFollowedId().getId());
		final var usersUserIdEntity = getEntityManager().find(UsersEntity.class,followRelationship.getUsersUserId().getId());
		persistAndFlush(new FollowRelationshipEntity(followRelationship ,usersFollowedIdEntity,usersUserIdEntity));
		
  	}

	@Override
	public Optional<FollowRelationship> findFollowRelationshipByPrimaryKey(String followedId,String userId) {
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(getEntityManager().find(UsersEntity.class,followedId));
		followRelationshipEntityKey.setUsersUserId(getEntityManager().find(UsersEntity.class,userId));
		return Optional.ofNullable(getEntityManager().find(FollowRelationshipEntity.class,followRelationshipEntityKey)).map(entityUtils::followRelationship);
		
	}


  	@Override
  	public void update(FollowRelationship followRelationship) {
		final var usersFollowedIdEntity= getEntityManager().find(UsersEntity.class, followRelationship.getUsersFollowedId().getId());
		final var usersUserIdEntity= getEntityManager().find(UsersEntity.class, followRelationship.getUsersUserId().getId());
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		final var followRelationshipEntity = getEntityManager().find(FollowRelationshipEntity.class, followRelationshipEntityKey);
		followRelationshipEntity.update(followRelationship ,usersFollowedIdEntity,usersUserIdEntity);
		
    }

  
  	@Override
  	public boolean delete(String followedId,String userId) {
		final var usersFollowedIdEntity=getEntityManager().find(UsersEntity.class, followedId);
		final var usersUserIdEntity=getEntityManager().find(UsersEntity.class, userId);
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		
		
		final var followRelationshipEntity = getEntityManager().find(FollowRelationshipEntity.class, followRelationshipEntityKey);
		try {
		     delete(followRelationshipEntity);
			 return true;
		} catch (Exception e){
			 return false;
		}
  	}

  	@Override
  	public PageResult<FollowRelationship> findFollowRelationshipByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<FollowRelationshipEntity> queryBuilder = null;

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
        final var followRelationshipResult = queryBuilder.list().stream()
        		                   .map(entityUtils::followRelationship)
        		                   .collect(Collectors.toList());
  		
    	final var total = followRelationshipResult.size();
    	return new PageResult<>(followRelationshipResult, total);
  	}

  	@Override
  	public long countFollowRelationship() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countFollowRelationshipQueryBuilder = new SimpleQueryBuilder();
    	countFollowRelationshipQueryBuilder.addQueryStatement("from FollowRelationshipEntity as followRelationship");
    
    	return count(countFollowRelationshipQueryBuilder.toQueryString(), params);
  	}
}
