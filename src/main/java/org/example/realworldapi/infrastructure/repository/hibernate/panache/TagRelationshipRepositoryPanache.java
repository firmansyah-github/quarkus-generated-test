package org.example.realworldapi.infrastructure.repository.hibernate.panache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.ApplicationScoped;

import org.example.realworldapi.application.web.resource.abs.FilterCondition;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.application.web.resource.abs.SortCondition;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipRepository;
import org.example.realworldapi.domain.model.util.PageResult;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.TagRelationshipEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.EntityUtils;
import org.example.realworldapi.infrastructure.repository.hibernate.panache.utils.SimpleQueryBuilder;
import org.example.realworldapi.domain.feature.FindTagRelationshipByPrimaryKey;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.TagRelationshipEntityKey;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.TagsEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.ArticlesEntity;

import java.time.LocalDateTime;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;
import lombok.AllArgsConstructor;

@ApplicationScoped
@AllArgsConstructor
public class TagRelationshipRepositoryPanache extends AbstractPanacheRepository<TagRelationshipEntity 		, FindTagRelationshipByPrimaryKey>
    implements TagRelationshipRepository {

  	private final EntityUtils entityUtils;

  	@Override
  	public void save(TagRelationship tagRelationship) {
		final var tagsTagIdEntity = getEntityManager().find(TagsEntity.class,tagRelationship.getTagsTagId().getId());
		final var articlesArticleIdEntity = getEntityManager().find(ArticlesEntity.class,tagRelationship.getArticlesArticleId().getId());
		persistAndFlush(new TagRelationshipEntity(tagRelationship ,tagsTagIdEntity,articlesArticleIdEntity));
		
  	}

	@Override
	public Optional<TagRelationship> findTagRelationshipByPrimaryKey(String articleId,String tagId) {
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(getEntityManager().find(ArticlesEntity.class,articleId));
		tagRelationshipEntityKey.setTagsTagId(getEntityManager().find(TagsEntity.class,tagId));
		return Optional.ofNullable(getEntityManager().find(TagRelationshipEntity.class,tagRelationshipEntityKey)).map(entityUtils::tagRelationship);
		
	}


  	@Override
  	public void update(TagRelationship tagRelationship) {
		final var tagsTagIdEntity= getEntityManager().find(TagsEntity.class, tagRelationship.getTagsTagId().getId());
		final var articlesArticleIdEntity= getEntityManager().find(ArticlesEntity.class, tagRelationship.getArticlesArticleId().getId());
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		final var tagRelationshipEntity = getEntityManager().find(TagRelationshipEntity.class, tagRelationshipEntityKey);
		tagRelationshipEntity.update(tagRelationship ,tagsTagIdEntity,articlesArticleIdEntity);
		
    }

  
  	@Override
  	public boolean delete(String articleId,String tagId) {
		final var tagsTagIdEntity=getEntityManager().find(TagsEntity.class, tagId);
		final var articlesArticleIdEntity=getEntityManager().find(ArticlesEntity.class, articleId);
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		
		
		final var tagRelationshipEntity = getEntityManager().find(TagRelationshipEntity.class, tagRelationshipEntityKey);
		try {
		     delete(tagRelationshipEntity);
			 return true;
		} catch (Exception e){
			 return false;
		}
  	}

  	@Override
  	public PageResult<TagRelationship> findTagRelationshipByFilter(ResourceFilter filter) {
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
        
        PanacheQuery<TagRelationshipEntity> queryBuilder = null;

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
        final var tagRelationshipResult = queryBuilder.list().stream()
        		                   .map(entityUtils::tagRelationship)
        		                   .collect(Collectors.toList());
  		
    	final var total = tagRelationshipResult.size();
    	return new PageResult<>(tagRelationshipResult, total);
  	}

  	@Override
  	public long countTagRelationship() {
    	Map<String, Object> params = new LinkedHashMap<>();
    	SimpleQueryBuilder countTagRelationshipQueryBuilder = new SimpleQueryBuilder();
    	countTagRelationshipQueryBuilder.addQueryStatement("from TagRelationshipEntity as tagRelationship");
    
    	return count(countTagRelationshipQueryBuilder.toQueryString(), params);
  	}
}