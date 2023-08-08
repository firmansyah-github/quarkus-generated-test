package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.NewTagRelationshipInput;

public interface CreateTagRelationship {
	TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput);
}
