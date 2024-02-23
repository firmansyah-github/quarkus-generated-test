// created by the factor : Feb 23, 2024, 6:45:22 AM  
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
