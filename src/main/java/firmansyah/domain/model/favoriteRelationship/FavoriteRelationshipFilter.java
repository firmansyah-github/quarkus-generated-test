// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
