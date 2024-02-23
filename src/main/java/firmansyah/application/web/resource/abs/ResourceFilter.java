// created by the factor : Feb 23, 2024, 6:45:22 AM  
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
