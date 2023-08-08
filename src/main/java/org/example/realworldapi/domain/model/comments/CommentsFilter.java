package org.example.realworldapi.domain.model.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class CommentsFilter {
	private final int offset;
	private final int limit;
	private String id;
	private String body;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String articleId;
	private String authorId;
}
