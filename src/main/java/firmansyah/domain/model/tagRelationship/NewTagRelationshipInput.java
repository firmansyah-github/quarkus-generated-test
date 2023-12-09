// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.tagRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewTagRelationshipInput {
	private String articleId;
	private String tagId;
}
