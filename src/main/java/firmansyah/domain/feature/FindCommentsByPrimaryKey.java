// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
            

import java.time.LocalDateTime;





public interface FindCommentsByPrimaryKey {
	Comments handle(String id);
}

