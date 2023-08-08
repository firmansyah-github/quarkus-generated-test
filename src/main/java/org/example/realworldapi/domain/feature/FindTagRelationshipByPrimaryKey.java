package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
            






public interface FindTagRelationshipByPrimaryKey {
	TagRelationship handle(String articleId, String tagId);
}

