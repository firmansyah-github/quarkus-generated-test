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
import firmansyah.domain.model.categories.Categories;
import firmansyah.domain.model.categories.CategoriesRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.CategoriesEntity;
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
public class CategoriesRepositoryPanache extends AbstractPanacheRepository<CategoriesEntity , Integer>
    implements CategoriesRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Categories categories) {
		persistAndFlush(new CategoriesEntity(categories ));
		
  	}

	@Override
	public Optional<Categories> findCategoriesByPrimaryKey(Integer categoryId) {
		return findByIdOptional(categoryId).map(entityUtils::categories);
	}


  	@Override
  	public void update(Categories categories) {
		final var categoriesEntity = findById(categories.getCategoryId());
		categoriesEntity.update(categories );
		
    }

  
  	@Override
  	public boolean delete(Integer categoryId) {
		return deleteById(categoryId);
  	}

  	@Override
  	public PageResult<Categories> findCategoriesByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<CategoriesEntity> queryBuilder = null;

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
        final var categoriesResult = queryBuilder.list().stream()
        		                   .map(entityUtils::categories)
        		                   .collect(Collectors.toList());
  		
    	final var total = categoriesResult.size();
    	return new PageResult<>(categoriesResult, total);
  	}

  	@Override
  	public long countCategories() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countCategoriesQueryBuilder = new SimpleQueryBuilder();
    	countCategoriesQueryBuilder.addQueryStatement("from CategoriesEntity as categories");
    
    	return count(countCategoriesQueryBuilder.toQueryString(), params);
  	}
}
