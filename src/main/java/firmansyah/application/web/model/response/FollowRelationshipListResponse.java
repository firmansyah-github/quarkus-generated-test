// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.application.web.model.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class FollowRelationshipListResponse {

	private List<FollowRelationshipResponse> followRelationship;
	private long followRelationshipCount;

	public FollowRelationshipListResponse(List<FollowRelationshipResponse> followRelationship, long followRelationshipCount) {
		this.followRelationship = followRelationship;
		this.followRelationshipCount = followRelationshipCount;
	}
}
