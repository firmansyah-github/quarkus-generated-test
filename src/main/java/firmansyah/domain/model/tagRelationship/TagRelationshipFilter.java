// created by the factor : Dec 11, 2023, 6:10:51 PM  
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
