// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.application.web.resource.abs;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author macddl01
 *
 */
@Data
@AllArgsConstructor
public class ResourceFilter {
	private final int offset;
	private final int limit;
	List<FilterCondition> filterConditions;
	List<SortCondition> sortConditions;

}
