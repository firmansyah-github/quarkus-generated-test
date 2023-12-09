// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
public class FavoriteRelationshipListResponse {

	private List<FavoriteRelationshipResponse> favoriteRelationship;
	private long favoriteRelationshipCount;

	public FavoriteRelationshipListResponse(List<FavoriteRelationshipResponse> favoriteRelationship, long favoriteRelationshipCount) {
		this.favoriteRelationship = favoriteRelationship;
		this.favoriteRelationshipCount = favoriteRelationshipCount;
	}
}
