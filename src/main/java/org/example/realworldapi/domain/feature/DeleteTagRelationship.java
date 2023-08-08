package org.example.realworldapi.domain.feature;


public interface DeleteTagRelationship {
	boolean handle(String articleId, String tagId);
}
