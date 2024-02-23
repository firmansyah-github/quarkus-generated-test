// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.tagRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class TagRelationshipFilter {
	private final int offset;
	private final int limit;
	private String articleId;
	private String tagId;
}
