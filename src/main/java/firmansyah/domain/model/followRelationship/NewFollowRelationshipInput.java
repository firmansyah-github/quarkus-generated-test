// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
