// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class CommentsFilter {
	private final int offset;
	private final int limit;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String articleId;
	private String authorId;
	private String body;
	private String id;
}
