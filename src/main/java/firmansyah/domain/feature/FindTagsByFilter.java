// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.tags.Tags;
import firmansyah.application.web.resource.abs.ResourceFilter;
import firmansyah.domain.model.util.PageResult;

public interface FindTagsByFilter {
	PageResult<Tags> handle(ResourceFilter resourceFilter);
}