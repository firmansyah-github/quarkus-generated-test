// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.UpdateCommentsInput;


public interface UpdateComments {
	Comments handle(UpdateCommentsInput updateCommentsInput);
}
