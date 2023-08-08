package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.DeleteTagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipRepository;


@AllArgsConstructor
public class DeleteTagRelationshipImpl implements DeleteTagRelationship {

	private final TagRelationshipRepository tagRelationshipRepository;

  	@Override
	public boolean handle(String articleId, String tagId) {
		return tagRelationshipRepository.delete(articleId, tagId);
	}
}
