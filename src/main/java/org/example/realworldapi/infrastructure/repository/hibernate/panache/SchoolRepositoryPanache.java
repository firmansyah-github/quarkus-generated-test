package org.example.realworldapi.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.example.realworldapi.application.web.resource.abs.FilterCondition;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.application.web.resource.abs.SortCondition;
import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.domain.model.school.SchoolRepository;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.SchoolEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.EntityUtils;
import org.example.realworldapi.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.String;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class SchoolRepositoryPanache extends AbstractPanacheRepository<SchoolEntity , String>
    implements SchoolRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(School school) {
		persistAndFlush(new SchoolEntity(school ));
		
  	}

	@Override
	public Optional<School> findSchoolByPrimaryKey(String id) {
		return findByIdOptional(id).map(entityUtils::school);
	}


  	@Override
  	public void update(School school) {
		final var schoolEntity = findById(school.getId());
		schoolEntity.update(school );
		
    }

  
  	@Override
  	public boolean delete(String id) {
		return deleteById(id);
  	}

  	@Override
  	public PageResult<School> findSchoolByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<SchoolEntity> queryBuilder = null;

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
        final var schoolResult = queryBuilder.list().stream()
        		                   .map(entityUtils::school)
        		                   .collect(Collectors.toList());
  		
    	final var total = schoolResult.size();
    	return new PageResult<>(schoolResult, total);
  	}

  	@Override
  	public long countSchool() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countSchoolQueryBuilder = new SimpleQueryBuilder();
    	countSchoolQueryBuilder.addQueryStatement("from SchoolEntity as school");
    
    	return count(countSchoolQueryBuilder.toQueryString(), params);
  	}
}
