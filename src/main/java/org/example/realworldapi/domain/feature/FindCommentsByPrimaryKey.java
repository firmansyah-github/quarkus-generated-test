package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.comments.Comments;
            

import java.time.LocalDateTime;





public interface FindCommentsByPrimaryKey {
	Comments handle(String id);
}

