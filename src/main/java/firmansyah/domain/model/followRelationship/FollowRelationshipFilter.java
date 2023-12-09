// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
