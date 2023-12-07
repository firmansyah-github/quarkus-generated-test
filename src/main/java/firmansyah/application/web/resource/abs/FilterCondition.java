// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
