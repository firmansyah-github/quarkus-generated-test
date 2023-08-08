package org.example.realworldapi.domain.model.comments;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class NewCommentsInput {
	private String id;
	private String body;
	private LocalDateTime createdat;
	private LocalDateTime updatedat;
	private String articleId;
	private String authorId;
}
