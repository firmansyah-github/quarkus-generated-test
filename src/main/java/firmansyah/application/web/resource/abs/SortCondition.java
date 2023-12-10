// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.application.web.resource.abs;

import lombok.Getter;
import lombok.Setter;

/**
 * @author macddl01
 *
 */
@Getter
@Setter
public class SortCondition {
	private String field;
	private String fieldSQL;
    private boolean descending;
}
