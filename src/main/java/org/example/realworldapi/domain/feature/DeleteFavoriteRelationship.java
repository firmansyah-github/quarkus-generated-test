package org.example.realworldapi.domain.feature;


public interface DeleteFavoriteRelationship {
	boolean handle(String articleId, String userId);
}
