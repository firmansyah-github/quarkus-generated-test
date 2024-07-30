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
import firmansyah.domain.model.products.Products;
import firmansyah.domain.model.products.ProductsRepository;
import firmansyah.domain.model.util.PageResult;
import firmansyah.infrastructure.repository.hibernate.entity.ProductsEntity;
import firmansyah.infrastructure.repository.hibernate.entity.EntityUtils;
import firmansyah.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import java.lang.Integer;
import firmansyah.infrastructure.repository.hibernate.entity.CategoriesEntity;
import firmansyah.infrastructure.repository.hibernate.entity.SuppliersEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class ProductsRepositoryPanache extends AbstractPanacheRepository<ProductsEntity , Integer>
    implements ProductsRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(Products products) {
		final var categoriesCategoryIdEntity = getEntityManager().find(CategoriesEntity.class,products.getCategoriesCategoryId().getCategoryId());
		final var suppliersSupplierIdEntity = getEntityManager().find(SuppliersEntity.class,products.getSuppliersSupplierId().getSupplierId());
		persistAndFlush(new ProductsEntity(products ,categoriesCategoryIdEntity,suppliersSupplierIdEntity));
		
  	}

	@Override
	public Optional<Products> findProductsByPrimaryKey(Integer productId) {
		return findByIdOptional(productId).map(entityUtils::products);
	}


  	@Override
  	public void update(Products products) {
		final var categoriesCategoryIdEntity= getEntityManager().find(CategoriesEntity.class, products.getCategoriesCategoryId().getCategoryId());
		final var suppliersSupplierIdEntity= getEntityManager().find(SuppliersEntity.class, products.getSuppliersSupplierId().getSupplierId());
		final var productsEntity = findById(products.getProductId());
		productsEntity.update(products ,categoriesCategoryIdEntity,suppliersSupplierIdEntity);
		
    }

  
  	@Override
  	public boolean delete(Integer productId) {
		return deleteById(productId);
  	}

  	@Override
  	public PageResult<Products> findProductsByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<ProductsEntity> queryBuilder = null;

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
        final var productsResult = queryBuilder.list().stream()
        		                   .map(entityUtils::products)
        		                   .collect(Collectors.toList());
  		
    	final var total = productsResult.size();
    	return new PageResult<>(productsResult, total);
  	}

  	@Override
  	public long countProducts() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countProductsQueryBuilder = new SimpleQueryBuilder();
    	countProductsQueryBuilder.addQueryStatement("from ProductsEntity as products");
    
    	return count(countProductsQueryBuilder.toQueryString(), params);
  	}
}
