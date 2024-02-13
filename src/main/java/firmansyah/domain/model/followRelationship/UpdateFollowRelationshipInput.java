// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.followRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateFollowRelationshipInput {
	private String followedId;
	private String userId;
}
