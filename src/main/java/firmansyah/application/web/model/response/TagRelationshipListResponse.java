// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
public class TagRelationshipListResponse {

	private List<TagRelationshipResponse> tagRelationship;
	private long tagRelationshipCount;

	public TagRelationshipListResponse(List<TagRelationshipResponse> tagRelationship, long tagRelationshipCount) {
		this.tagRelationship = tagRelationship;
		this.tagRelationshipCount = tagRelationshipCount;
	}
}
