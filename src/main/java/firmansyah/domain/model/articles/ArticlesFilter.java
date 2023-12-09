// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.articles;

import lombok.AllArgsConstructor;
import lombok.Data;

            

import java.time.LocalDateTime;

import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.comments.Comments;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class ArticlesFilter {
	private final int offset;
	private final int limit;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String authorId;
	private String body;
	private String description;
	private String id;
	private String slug;
	private String title;
}
