// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.model.favoriteRelationship;

import lombok.AllArgsConstructor;
import lombok.Data;

            




@Data
@AllArgsConstructor
public class FavoriteRelationshipFilter {
	private final int offset;
	private final int limit;
	private String articleId;
	private String userId;
}
