package org.example.realworldapi.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.model.articles.Articles;
import org.example.realworldapi.domain.model.articles.ArticlesModelBuilder;
import org.example.realworldapi.domain.model.comments.Comments;
import org.example.realworldapi.domain.model.comments.CommentsModelBuilder;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationship;
import org.example.realworldapi.domain.model.followRelationship.FollowRelationshipModelBuilder;
import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.domain.model.school.SchoolModelBuilder;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipModelBuilder;
import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.domain.model.tags.TagsModelBuilder;
import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.users.UsersModelBuilder;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationship;
import org.example.realworldapi.domain.model.favoriteRelationship.FavoriteRelationshipModelBuilder;

import org.example.realworldapi.domain.model.user.User;
import org.example.realworldapi.domain.model.user.UserModelBuilder;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
public class EntityUtils {
	private final ArticlesModelBuilder articlesBuilder;
	private final CommentsModelBuilder commentsBuilder;
	private final FollowRelationshipModelBuilder followRelationshipBuilder;
	private final SchoolModelBuilder schoolBuilder;
	private final TagRelationshipModelBuilder tagRelationshipBuilder;
	private final TagsModelBuilder tagsBuilder;
	private final UsersModelBuilder usersBuilder;
	private final FavoriteRelationshipModelBuilder favoriteRelationshipBuilder;
	private final UserModelBuilder userBuilder;

	public Articles articles(ArticlesEntity articlesEntity) {
    	return articlesBuilder.build(
				articlesEntity.getId(),
				articlesEntity.getBody(),
				articlesEntity.getDescription(),
				articlesEntity.getSlug(),
				articlesEntity.getTitle(),
				articlesEntity.getUpdatedat(),
				null,
				null,
				null,
				users(articlesEntity.getUsersAuthorId())
        );
	}
	public Comments comments(CommentsEntity commentsEntity) {
    	return commentsBuilder.build(
				commentsEntity.getId(),
				commentsEntity.getBody(),
				commentsEntity.getCreatedat(),
				commentsEntity.getUpdatedat(),
				users(commentsEntity.getUsersAuthorId()),
				articles(commentsEntity.getArticlesArticleId())
        );
	}
	public FollowRelationship followRelationship(FollowRelationshipEntity followRelationshipEntity) {
    	return followRelationshipBuilder.build(
				users(followRelationshipEntity.getUsersFollowedId()),
				users(followRelationshipEntity.getUsersUserId())
        );
	}
	public School school(SchoolEntity schoolEntity) {
    	return schoolBuilder.build(
				schoolEntity.getId(),
				schoolEntity.getName()
        );
	}
	public TagRelationship tagRelationship(TagRelationshipEntity tagRelationshipEntity) {
    	return tagRelationshipBuilder.build(
				tags(tagRelationshipEntity.getTagsTagId()),
				articles(tagRelationshipEntity.getArticlesArticleId())
        );
	}
	public Tags tags(TagsEntity tagsEntity) {
    	return tagsBuilder.build(
				tagsEntity.getId(),
				tagsEntity.getName(),
				null
        );
	}
	public Users users(UsersEntity usersEntity) {
    	return usersBuilder.build(
				usersEntity.getId(),
				usersEntity.getBio(),
				usersEntity.getEmail(),
				usersEntity.getImage(),
				usersEntity.getPassword(),
				usersEntity.getUsername(),
				null,
				null,
				null,
				null,
				null
        );
	}
	public FavoriteRelationship favoriteRelationship(FavoriteRelationshipEntity favoriteRelationshipEntity) {
    	return favoriteRelationshipBuilder.build(
				users(favoriteRelationshipEntity.getUsersUserId()),
				articles(favoriteRelationshipEntity.getArticlesArticleId())
        );
	}

     public User user(UserEntity userEntity) {
	    final var id = userEntity.getId();
	    final var username = userEntity.getUsername();
	    final var bio = userEntity.getBio();
	    final var image = userEntity.getImage();
	    final var password = userEntity.getPassword();
	    final var email = userEntity.getEmail();
	    return userBuilder.build(id, username, bio, image, password, email);
 	 }

 
}
