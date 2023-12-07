// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
            

import java.time.LocalDateTime;





public interface FindCommentsByPrimaryKey {
	Comments handle(String id);
}

