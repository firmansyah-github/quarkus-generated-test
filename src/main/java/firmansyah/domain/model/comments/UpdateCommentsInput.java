// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;




@Data
@AllArgsConstructor
public class UpdateCommentsInput {
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String articleId;
	private String authorId;
	private String body;
	private String id;
}
