// created by the factor : Dec 11, 2023, 5:57:49 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.UpdateCommentsInput;


public interface UpdateComments {
	Comments handle(UpdateCommentsInput updateCommentsInput);
}
