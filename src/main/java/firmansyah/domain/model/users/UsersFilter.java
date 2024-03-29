// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.articles.Articles;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class UsersFilter {
	private final int offset;
	private final int limit;
	private String bio;
	private String email;
	private String id;
	private String image;
	private String password;
	private String username;
}
