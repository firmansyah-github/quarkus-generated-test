package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.domain.model.comments.NewCommentsInput;

public interface CreateComments {
	Comments handle(NewCommentsInput newCommentsInput);
}
