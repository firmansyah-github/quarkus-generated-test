// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import firmansyah.application.web.resource.abs.FilterCondition;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.application.web.resource.abs.SortCondition;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.FavoriteRelationshipEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import firmansyah.domain.feature.FindFavoriteRelationshipByPrimaryKey;
import firmansyah.infrastructure.repository.hibernate.entity.FavoriteRelationshipEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;
import firmansyah.infrastructure.repository.hibernate.entity.ArticlesEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class FavoriteRelationshipRepositoryPanache extends AbstractPanacheRepository<FavoriteRelationshipEntity 		, FindFavoriteRelationshipByPrimaryKey>
    implements FavoriteRelationshipRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(FavoriteRelationship favoriteRelationship) {
		final var usersUserIdEntity = getEntityManager().find(UsersEntity.class,favoriteRelationship.getUsersUserId().getId());
		final var articlesArticleIdEntity = getEntityManager().find(ArticlesEntity.class,favoriteRelationship.getArticlesArticleId().getId());
		persistAndFlush(new FavoriteRelationshipEntity(favoriteRelationship ,usersUserIdEntity,articlesArticleIdEntity));
		
  	}

	@Override
	public Optional<FavoriteRelationship> findFavoriteRelationshipByPrimaryKey(String articleId,String userId) {
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		favoriteRelationshipEntityKey.setArticlesArticleId(getEntityManager().find(ArticlesEntity.class,articleId));
		favoriteRelationshipEntityKey.setUsersUserId(getEntityManager().find(UsersEntity.class,userId));
		return Optional.ofNullable(getEntityManager().find(FavoriteRelationshipEntity.class,favoriteRelationshipEntityKey)).map(entityUtils::favoriteRelationship);
		
	}


  	@Override
  	public void update(FavoriteRelationship favoriteRelationship) {
		final var usersUserIdEntity= getEntityManager().find(UsersEntity.class, favoriteRelationship.getUsersUserId().getId());
		final var articlesArticleIdEntity= getEntityManager().find(ArticlesEntity.class, favoriteRelationship.getArticlesArticleId().getId());
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		final var favoriteRelationshipEntity = getEntityManager().find(FavoriteRelationshipEntity.class, favoriteRelationshipEntityKey);
		favoriteRelationshipEntity.update(favoriteRelationship ,usersUserIdEntity,articlesArticleIdEntity);
		
    }

  
  	@Override
  	public boolean delete(String articleId,String userId) {
		final var usersUserIdEntity=getEntityManager().find(UsersEntity.class, userId);
		final var articlesArticleIdEntity=getEntityManager().find(ArticlesEntity.class, articleId);
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		
		
		final var favoriteRelationshipEntity = getEntityManager().find(FavoriteRelationshipEntity.class, favoriteRelationshipEntityKey);
		try {
		     delete(favoriteRelationshipEntity);
			 return true;
		} catch (Exception e){
			 return false;
		}
  	}

  	@Override
  	public PageResult<FavoriteRelationship> findFavoriteRelationshipByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<FavoriteRelationshipEntity> queryBuilder = null;

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
        final var favoriteRelationshipResult = queryBuilder.list().stream()
        		                   .map(entityUtils::favoriteRelationship)
        		                   .collect(Collectors.toList());
  		
    	final var total = favoriteRelationshipResult.size();
    	return new PageResult<>(favoriteRelationshipResult, total);
  	}

  	@Override
  	public long countFavoriteRelationship() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countFavoriteRelationshipQueryBuilder = new SimpleQueryBuilder();
    	countFavoriteRelationshipQueryBuilder.addQueryStatement("from FavoriteRelationshipEntity as favoriteRelationship");
    
    	return count(countFavoriteRelationshipQueryBuilder.toQueryString(), params);
  	}
}
