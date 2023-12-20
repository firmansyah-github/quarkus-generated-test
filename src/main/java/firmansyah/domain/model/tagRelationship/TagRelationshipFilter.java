// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
