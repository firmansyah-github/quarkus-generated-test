package org.example.realworldapi.domain.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.domain.model.articles.Articles;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class UsersFilter {
	private final int offset;
	private final int limit;
	private String id;
	private String bio;
	private String email;
	private String image;
	private String password;
	private String username;
}
