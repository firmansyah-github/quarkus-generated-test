// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
