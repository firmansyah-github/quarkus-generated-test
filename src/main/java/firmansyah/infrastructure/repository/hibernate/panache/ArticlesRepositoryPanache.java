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
import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.ArticlesRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.ArticlesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.String;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ArticlesRepositoryPanache extends AbstractPanacheRepository<ArticlesEntity , String>
    implements ArticlesRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Articles articles) {
		final var usersAuthorIdEntity = getEntityManager().find(UsersEntity.class,articles.getUsersAuthorId().getId());
		persistAndFlush(new ArticlesEntity(articles ,usersAuthorIdEntity));
		
  	}

	@Override
	public Optional<Articles> findArticlesByPrimaryKey(String id) {
		return findByIdOptional(id).map(entityUtils::articles);
	}


  	@Override
  	public void update(Articles articles) {
		final var usersAuthorIdEntity= getEntityManager().find(UsersEntity.class, articles.getUsersAuthorId().getId());
		final var articlesEntity = findById(articles.getId());
		articlesEntity.update(articles ,usersAuthorIdEntity);
		
    }

  
  	@Override
  	public boolean delete(String id) {
		return deleteById(id);
  	}

  	@Override
  	public PageResult<Articles> findArticlesByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<ArticlesEntity> queryBuilder = null;

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
        final var articlesResult = queryBuilder.list().stream()
        		                   .map(entityUtils::articles)
        		                   .collect(Collectors.toList());
  		
    	final var total = articlesResult.size();
    	return new PageResult<>(articlesResult, total);
  	}

  	@Override
  	public long countArticles() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countArticlesQueryBuilder = new SimpleQueryBuilder();
    	countArticlesQueryBuilder.addQueryStatement("from ArticlesEntity as articles");
    
    	return count(countArticlesQueryBuilder.toQueryString(), params);
  	}
}
