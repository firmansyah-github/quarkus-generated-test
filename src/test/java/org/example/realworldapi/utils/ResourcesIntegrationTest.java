package org.example.realworldapi.utils;

import org.example.realworldapi.infrastructure.repository.hibernate.entity.ArticlesEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.CommentsEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.FollowRelationshipEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.FollowRelationshipEntityKey;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.SchoolEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.TagRelationshipEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.TagRelationshipEntityKey;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.TagsEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.UsersEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.FavoriteRelationshipEntity;
import org.example.realworldapi.infrastructure.repository.hibernate.entity.FavoriteRelationshipEntityKey;
import java.time.LocalDateTime;
import java.util.UUID;



public class ResourcesIntegrationTest extends AbstractIntegrationTest {
	protected ArticlesEntity createArticles(String unique){
		final var usersAuthorIdEntity= createUsers(unique);
		return transaction(
        () -> {
		final var articlesEntity = new ArticlesEntity();
		articlesEntity.setId(UUID.randomUUID().toString()+unique);
		articlesEntity.setBody("body"+unique);
		articlesEntity.setDescription("description"+unique);
		articlesEntity.setSlug("slug"+unique);
		articlesEntity.setTitle("title"+unique);
		articlesEntity.setUpdatedat(LocalDateTime.now());
		articlesEntity.setUsersAuthorId(usersAuthorIdEntity);
          	entityManager.persist(articlesEntity);
            return articlesEntity;
        });
	}
	
	protected ArticlesEntity findArticlesEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(ArticlesEntity.class, id));
	}
  
	protected CommentsEntity createComments(String unique){
		final var usersAuthorIdEntity= createUsers(unique);
		final var articlesArticleIdEntity= createArticles(unique);
		return transaction(
        () -> {
		final var commentsEntity = new CommentsEntity();
		commentsEntity.setId(UUID.randomUUID().toString()+unique);
		commentsEntity.setBody("body"+unique);
		commentsEntity.setCreatedat(LocalDateTime.now());
		commentsEntity.setUpdatedat(LocalDateTime.now());
		commentsEntity.setUsersAuthorId(usersAuthorIdEntity);
		commentsEntity.setArticlesArticleId(articlesArticleIdEntity);
          	entityManager.persist(commentsEntity);
            return commentsEntity;
        });
	}
	
	protected CommentsEntity findCommentsEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(CommentsEntity.class, id));
	}
  
	protected FollowRelationshipEntity createFollowRelationship(String unique){
		final var usersFollowedIdEntity= createUsers(unique);
		final var usersUserIdEntity= createUsers(unique);
		return transaction(
        () -> {
		final var followRelationshipEntity = new FollowRelationshipEntity();
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		followRelationshipEntity.setPrimaryKey(followRelationshipEntityKey);
		followRelationshipEntity.setUsersFollowedId(usersFollowedIdEntity);
		followRelationshipEntity.setUsersUserId(usersUserIdEntity);
          	entityManager.persist(followRelationshipEntity);
            return followRelationshipEntity;
        });
	}
	
	protected FollowRelationshipEntity findFollowRelationshipEntityByPK(String followedId,String userId) {
	    
		final var followRelationshipEntityKey = new FollowRelationshipEntityKey();
		final var usersFollowedIdEntity = entityManager.find(UsersEntity.class, followedId);
		followRelationshipEntityKey.setUsersFollowedId(usersFollowedIdEntity);
		
		final var usersUserIdEntity = entityManager.find(UsersEntity.class, userId);
		followRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		
		
		return transaction(() -> entityManager.find(FollowRelationshipEntity.class, followRelationshipEntityKey));
	}
  
	protected SchoolEntity createSchool(String unique){
		return transaction(
        () -> {
		final var schoolEntity = new SchoolEntity();
		schoolEntity.setId(UUID.randomUUID().toString()+unique);
		schoolEntity.setName("name"+unique);
          	entityManager.persist(schoolEntity);
            return schoolEntity;
        });
	}
	
	protected SchoolEntity findSchoolEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(SchoolEntity.class, id));
	}
  
	protected TagRelationshipEntity createTagRelationship(String unique){
		final var tagsTagIdEntity= createTags(unique);
		final var articlesArticleIdEntity= createArticles(unique);
		return transaction(
        () -> {
		final var tagRelationshipEntity = new TagRelationshipEntity();
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		tagRelationshipEntity.setPrimaryKey(tagRelationshipEntityKey);
		tagRelationshipEntity.setTagsTagId(tagsTagIdEntity);
		tagRelationshipEntity.setArticlesArticleId(articlesArticleIdEntity);
          	entityManager.persist(tagRelationshipEntity);
            return tagRelationshipEntity;
        });
	}
	
	protected TagRelationshipEntity findTagRelationshipEntityByPK(String articleId,String tagId) {
	    
		final var tagRelationshipEntityKey = new TagRelationshipEntityKey();
		final var articlesArticleIdEntity = entityManager.find(ArticlesEntity.class, articleId);
		tagRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		
		final var tagsTagIdEntity = entityManager.find(TagsEntity.class, tagId);
		tagRelationshipEntityKey.setTagsTagId(tagsTagIdEntity);
		
		
		return transaction(() -> entityManager.find(TagRelationshipEntity.class, tagRelationshipEntityKey));
	}
  
	protected TagsEntity createTags(String unique){
		return transaction(
        () -> {
		final var tagsEntity = new TagsEntity();
		tagsEntity.setId(UUID.randomUUID().toString()+unique);
		tagsEntity.setName("name"+unique);
          	entityManager.persist(tagsEntity);
            return tagsEntity;
        });
	}
	
	protected TagsEntity findTagsEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(TagsEntity.class, id));
	}
  
	protected UsersEntity createUsers(String unique){
		return transaction(
        () -> {
		final var usersEntity = new UsersEntity();
		usersEntity.setId(UUID.randomUUID().toString()+unique);
		usersEntity.setBio("bio"+unique);
		usersEntity.setEmail("email"+unique);
		usersEntity.setImage("image"+unique);
		usersEntity.setPassword("password"+unique);
		usersEntity.setUsername("username"+unique);
          	entityManager.persist(usersEntity);
            return usersEntity;
        });
	}
	
	protected UsersEntity findUsersEntityByPK(String id) {
	    
		return transaction(() -> entityManager.find(UsersEntity.class, id));
	}
  
	protected FavoriteRelationshipEntity createFavoriteRelationship(String unique){
		final var usersUserIdEntity= createUsers(unique);
		final var articlesArticleIdEntity= createArticles(unique);
		return transaction(
        () -> {
		final var favoriteRelationshipEntity = new FavoriteRelationshipEntity();
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		favoriteRelationshipEntity.setPrimaryKey(favoriteRelationshipEntityKey);
		favoriteRelationshipEntity.setUsersUserId(usersUserIdEntity);
		favoriteRelationshipEntity.setArticlesArticleId(articlesArticleIdEntity);
          	entityManager.persist(favoriteRelationshipEntity);
            return favoriteRelationshipEntity;
        });
	}
	
	protected FavoriteRelationshipEntity findFavoriteRelationshipEntityByPK(String articleId,String userId) {
	    
		final var favoriteRelationshipEntityKey = new FavoriteRelationshipEntityKey();
		final var articlesArticleIdEntity = entityManager.find(ArticlesEntity.class, articleId);
		favoriteRelationshipEntityKey.setArticlesArticleId(articlesArticleIdEntity);
		
		final var usersUserIdEntity = entityManager.find(UsersEntity.class, userId);
		favoriteRelationshipEntityKey.setUsersUserId(usersUserIdEntity);
		
		
		return transaction(() -> entityManager.find(FavoriteRelationshipEntity.class, favoriteRelationshipEntityKey));
	}
  
  

}
