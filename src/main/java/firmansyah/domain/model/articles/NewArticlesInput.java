// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.model.articles;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class NewArticlesInput {
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String authorId;
	private String body;
	private String description;
	private String id;
	private String slug;
	private String title;
}
