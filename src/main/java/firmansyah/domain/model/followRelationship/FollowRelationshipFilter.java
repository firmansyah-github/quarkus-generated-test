// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
