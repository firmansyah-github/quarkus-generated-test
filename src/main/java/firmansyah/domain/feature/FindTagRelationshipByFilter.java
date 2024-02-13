// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindTagRelationshipByFilter {
	PageResult<TagRelationship> handle(ResourceFilter resourceFilter);
}