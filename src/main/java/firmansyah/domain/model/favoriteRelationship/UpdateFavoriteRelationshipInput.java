// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.model.favoriteRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateFavoriteRelationshipInput {
	private String articleId;
	private String userId;
}
