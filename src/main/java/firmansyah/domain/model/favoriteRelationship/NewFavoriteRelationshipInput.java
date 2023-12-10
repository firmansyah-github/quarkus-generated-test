// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.model.favoriteRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewFavoriteRelationshipInput {
	private String articleId;
	private String userId;
}
