// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.NewCommentsInput;

public interface CreateComments {
	Comments handle(NewCommentsInput newCommentsInput);
}
