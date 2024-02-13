// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.followRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewFollowRelationshipInput {
	private String followedId;
	private String userId;
}
