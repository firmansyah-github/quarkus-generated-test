package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.domain.model.comments.UpdateCommentsInput;


public interface UpdateComments {
	Comments handle(UpdateCommentsInput updateCommentsInput);
}
