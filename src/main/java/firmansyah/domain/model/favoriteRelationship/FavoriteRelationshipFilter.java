// created by the factor : Feb 13, 2024, 4:07:37 PM  
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
