// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.UpdateCommentsInput;


public interface UpdateComments {
	Comments handle(UpdateCommentsInput updateCommentsInput);
}
