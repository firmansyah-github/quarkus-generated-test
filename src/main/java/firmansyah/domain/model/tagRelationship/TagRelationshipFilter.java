// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
