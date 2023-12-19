// created by the factor : Dec 17, 2023, 6:05:00 PM  
package firmansyah.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.enterprise.context.ApplicationScoped;

import firmansyah.application.web.resource.abs.FilterCondition;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.application.web.resource.abs.SortCondition;
import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.TagsRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.TagsEntity;
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
public class TagsRepositoryPanache extends AbstractPanacheRepository<TagsEntity , String>
    implements TagsRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Tags tags) {
		persistAndFlush(new TagsEntity(tags ));
		
  	}

	@Override
	public Optional<Tags> findTagsByPrimaryKey(String id) {
		return findByIdOptional(id).map(entityUtils::tags);
	}


  	@Override
  	public void update(Tags tags) {
		final var tagsEntity = findById(tags.getId());
		tagsEntity.update(tags );
		
    }

  
  	@Override
  	public boolean delete(String id) {
		return deleteById(id);
  	}

  	@Override
  	public PageResult<Tags> findTagsByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<TagsEntity> queryBuilder = null;

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
        queryBuilder.page(filter.getOffset(), filter.getLimit());

        // Execute the query
        final var tagsResult = queryBuilder.list().stream()
        		                   .map(entityUtils::tags)
        		                   .collect(Collectors.toList());
  		
    	final var total = tagsResult.size();
    	return new PageResult<>(tagsResult, total);
  	}

  	@Override
  	public long countTags() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countTagsQueryBuilder = new SimpleQueryBuilder();
    	countTagsQueryBuilder.addQueryStatement("from TagsEntity as tags");
    
    	return count(countTagsQueryBuilder.toQueryString(), params);
  	}
}
