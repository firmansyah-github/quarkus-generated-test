// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.application.web.resource.abs;

import lombok.Getter;
import lombok.Setter;

/**
 * @author macddl01
 *
 */
@Getter
@Setter
public class FilterCondition {
	private String field;
	private String fieldSQL;
	private String operator;
	private String operatorSQL;
	private String value;
	private String conjunction;
}
