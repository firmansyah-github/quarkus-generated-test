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
import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.employeeTerritories.EmployeeTerritoriesRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeeTerritoriesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import firmansyah.domain.feature.FindEmployeeTerritoriesByPrimaryKey;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeeTerritoriesEntityKey;
import firmansyah.infrastructure.repository.hibernate.entity.EmployeesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.TerritoriesEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class EmployeeTerritoriesRepositoryPanache extends AbstractPanacheRepository<EmployeeTerritoriesEntity 		, FindEmployeeTerritoriesByPrimaryKey>
    implements EmployeeTerritoriesRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(EmployeeTerritories employeeTerritories) {
		final var employeesEmployeeIdEntity = getEntityManager().find(EmployeesEntity.class,employeeTerritories.getEmployeesEmployeeId().getEmployeeId());
		final var territoriesTerritoryIdEntity = getEntityManager().find(TerritoriesEntity.class,employeeTerritories.getTerritoriesTerritoryId().getTerritoryId());
		persistAndFlush(new EmployeeTerritoriesEntity(employeeTerritories ,employeesEmployeeIdEntity,territoriesTerritoryIdEntity));
		
  	}

	@Override
	public Optional<EmployeeTerritories> findEmployeeTerritoriesByPrimaryKey(Integer employeeId,String territoryId) {
		final var employeeTerritoriesEntityKey = new EmployeeTerritoriesEntityKey();
		employeeTerritoriesEntityKey.setEmployeesEmployeeId(getEntityManager().find(EmployeesEntity.class,employeeId));
		employeeTerritoriesEntityKey.setTerritoriesTerritoryId(getEntityManager().find(TerritoriesEntity.class,territoryId));
		return Optional.ofNullable(getEntityManager().find(EmployeeTerritoriesEntity.class,employeeTerritoriesEntityKey)).map(entityUtils::employeeTerritories);
		
	}


  	@Override
  	public void update(EmployeeTerritories employeeTerritories) {
		final var employeesEmployeeIdEntity= getEntityManager().find(EmployeesEntity.class, employeeTerritories.getEmployeesEmployeeId().getEmployeeId());
		final var territoriesTerritoryIdEntity= getEntityManager().find(TerritoriesEntity.class, employeeTerritories.getTerritoriesTerritoryId().getTerritoryId());
		final var employeeTerritoriesEntityKey = new EmployeeTerritoriesEntityKey();
		employeeTerritoriesEntityKey.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		employeeTerritoriesEntityKey.setTerritoriesTerritoryId(territoriesTerritoryIdEntity);
		final var employeeTerritoriesEntity = getEntityManager().find(EmployeeTerritoriesEntity.class, employeeTerritoriesEntityKey);
		employeeTerritoriesEntity.update(employeeTerritories ,employeesEmployeeIdEntity,territoriesTerritoryIdEntity);
		
    }

  
  	@Override
  	public boolean delete(Integer employeeId,String territoryId) {
		final var employeesEmployeeIdEntity=getEntityManager().find(EmployeesEntity.class, employeeId);
		final var territoriesTerritoryIdEntity=getEntityManager().find(TerritoriesEntity.class, territoryId);
		final var employeeTerritoriesEntityKey = new EmployeeTerritoriesEntityKey();
		employeeTerritoriesEntityKey.setEmployeesEmployeeId(employeesEmployeeIdEntity);
		employeeTerritoriesEntityKey.setTerritoriesTerritoryId(territoriesTerritoryIdEntity);
		
		
		final var employeeTerritoriesEntity = getEntityManager().find(EmployeeTerritoriesEntity.class, employeeTerritoriesEntityKey);
		try {
		     delete(employeeTerritoriesEntity);
			 return true;
		} catch (Exception e){
			 return false;
		}
  	}

  	@Override
  	public PageResult<EmployeeTerritories> findEmployeeTerritoriesByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<EmployeeTerritoriesEntity> queryBuilder = null;

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
        final var employeeTerritoriesResult = queryBuilder.list().stream()
        		                   .map(entityUtils::employeeTerritories)
        		                   .collect(Collectors.toList());
  		
    	final var total = employeeTerritoriesResult.size();
    	return new PageResult<>(employeeTerritoriesResult, total);
  	}

  	@Override
  	public long countEmployeeTerritories() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countEmployeeTerritoriesQueryBuilder = new SimpleQueryBuilder();
    	countEmployeeTerritoriesQueryBuilder.addQueryStatement("from EmployeeTerritoriesEntity as employeeTerritories");
    
    	return count(countEmployeeTerritoriesQueryBuilder.toQueryString(), params);
  	}
}
