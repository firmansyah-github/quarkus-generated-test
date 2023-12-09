// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
