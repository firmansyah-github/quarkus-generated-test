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
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.CommentsRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.CommentsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.String;
import firmansyah.infrastructure.repository.hibernate.entity.ArticlesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.UsersEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class CommentsRepositoryPanache extends AbstractPanacheRepository<CommentsEntity , String>
    implements CommentsRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Comments comments) {
		final var articlesArticleIdEntity = getEntityManager().find(ArticlesEntity.class,comments.getArticlesArticleId().getId());
		final var usersAuthorIdEntity = getEntityManager().find(UsersEntity.class,comments.getUsersAuthorId().getId());
		persistAndFlush(new CommentsEntity(comments ,articlesArticleIdEntity,usersAuthorIdEntity));
		
  	}

	@Override
	public Optional<Comments> findCommentsByPrimaryKey(String id) {
		return findByIdOptional(id).map(entityUtils::comments);
	}


  	@Override
  	public void update(Comments comments) {
		final var articlesArticleIdEntity= getEntityManager().find(ArticlesEntity.class, comments.getArticlesArticleId().getId());
		final var usersAuthorIdEntity= getEntityManager().find(UsersEntity.class, comments.getUsersAuthorId().getId());
		final var commentsEntity = findById(comments.getId());
		commentsEntity.update(comments ,articlesArticleIdEntity,usersAuthorIdEntity);
		
    }

  
  	@Override
  	public boolean delete(String id) {
		return deleteById(id);
  	}

  	@Override
  	public PageResult<Comments> findCommentsByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<CommentsEntity> queryBuilder = null;

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
        final var commentsResult = queryBuilder.list().stream()
        		                   .map(entityUtils::comments)
        		                   .collect(Collectors.toList());
  		
    	final var total = commentsResult.size();
    	return new PageResult<>(commentsResult, total);
  	}

  	@Override
  	public long countComments() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countCommentsQueryBuilder = new SimpleQueryBuilder();
    	countCommentsQueryBuilder.addQueryStatement("from CommentsEntity as comments");
    
    	return count(countCommentsQueryBuilder.toQueryString(), params);
  	}
}
