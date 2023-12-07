// created by the factor : Dec 7, 2023, 4:03:00 PM  
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
