// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.model.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class NewCommentsInput {
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String articleId;
	private String authorId;
	private String body;
	private String id;
}
