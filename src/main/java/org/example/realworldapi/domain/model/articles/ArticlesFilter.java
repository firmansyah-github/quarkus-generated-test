package org.example.realworldapi.domain.model.articles;

import lombok.AllArgsConstructor;
import lombok.Data;

            

import java.time.LocalDateTime;

import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.comments.Comments;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class ArticlesFilter {
	private final int offset;
	private final int limit;
	private String id;
	private String body;
	private String description;
	private String slug;
	private String title;
	private LocalDateTime updatedat;
	private String authorId;
}
