package org.example.realworldapi.domain.model.articles;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;




@Data
@AllArgsConstructor
public class UpdateArticlesInput {
	private String id;
	private String body;
	private String description;
	private String slug;
	private String title;
	private LocalDateTime updatedat;
	private String authorId;
}
