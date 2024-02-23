// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
            

import java.time.LocalDateTime;





public interface FindCommentsByPrimaryKey {
	Comments handle(String id);
}

