// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.articles.Articles;
import firmansyah.domain.model.articles.ArticlesModelBuilder;
import firmansyah.domain.model.comments.Comments;
import firmansyah.domain.model.comments.CommentsModelBuilder;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationship;
import firmansyah.domain.model.favoriteRelationship.FavoriteRelationshipModelBuilder;
import firmansyah.domain.model.followRelationship.FollowRelationship;
import firmansyah.domain.model.followRelationship.FollowRelationshipModelBuilder;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationshipModelBuilder;
import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.TagsModelBuilder;
import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UsersModelBuilder;




import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@AllArgsConstructor
public class EntityUtils {
	private final ArticlesModelBuilder articlesBuilder;
	private final CommentsModelBuilder commentsBuilder;
	private final FavoriteRelationshipModelBuilder favoriteRelationshipBuilder;
	private final FollowRelationshipModelBuilder followRelationshipBuilder;
	private final TagRelationshipModelBuilder tagRelationshipBuilder;
	private final TagsModelBuilder tagsBuilder;
	private final UsersModelBuilder usersBuilder;
	

	public Articles articles(ArticlesEntity articlesEntity) {
    	return articlesBuilder.build(
				articlesEntity.getCreatedat(),
				articlesEntity.getUpdatedat(),
				articlesEntity.getBody(),
				articlesEntity.getDescription(),
				articlesEntity.getId(),
				articlesEntity.getSlug(),
				articlesEntity.getTitle(),
				null,
				null,
				null,
				users(articlesEntity.getUsersAuthorId())
        );
	}
	public Comments comments(CommentsEntity commentsEntity) {
    	return commentsBuilder.build(
				commentsEntity.getCreatedat(),
				commentsEntity.getUpdatedat(),
				commentsEntity.getBody(),
				commentsEntity.getId(),
				users(commentsEntity.getUsersAuthorId()),
				articles(commentsEntity.getArticlesArticleId())
        );
	}
	public FavoriteRelationship favoriteRelationship(FavoriteRelationshipEntity favoriteRelationshipEntity) {
    	return favoriteRelationshipBuilder.build(
				users(favoriteRelationshipEntity.getUsersUserId()),
				articles(favoriteRelationshipEntity.getArticlesArticleId())
        );
	}
	public FollowRelationship followRelationship(FollowRelationshipEntity followRelationshipEntity) {
    	return followRelationshipBuilder.build(
				users(followRelationshipEntity.getUsersFollowedId()),
				users(followRelationshipEntity.getUsersUserId())
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
				usersEntity.getBio(),
				usersEntity.getEmail(),
				usersEntity.getId(),
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

    
}
