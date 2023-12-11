// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.NewCommentsInput;

public interface CreateComments {
	Comments handle(NewCommentsInput newCommentsInput);
}
