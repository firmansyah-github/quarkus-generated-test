// created by the factor : Dec 11, 2023, 6:10:51 PM  
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
