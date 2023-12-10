// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.model.followRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class FollowRelationshipFilter {
	private final int offset;
	private final int limit;
	private String followedId;
	private String userId;
}
