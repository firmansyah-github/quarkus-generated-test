// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
            

import java.time.LocalDateTime;





public interface FindCommentsByPrimaryKey {
	Comments handle(String id);
}

