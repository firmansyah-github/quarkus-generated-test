// created by the factor : Dec 11, 2023, 5:57:49 AM  
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
