// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.tagRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateTagRelationshipInput {
	private String articleId;
	private String tagId;
}
