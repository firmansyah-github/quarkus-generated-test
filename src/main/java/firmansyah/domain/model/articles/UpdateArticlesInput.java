// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.articles;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;




@Data
@AllArgsConstructor
public class UpdateArticlesInput {
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String authorId;
	private String body;
	private String description;
	private String id;
	private String slug;
	private String title;
}
