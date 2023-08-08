package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.UpdateTagRelationshipInput;


public interface UpdateTagRelationship {
	TagRelationship handle(UpdateTagRelationshipInput updateTagRelationshipInput);
}
