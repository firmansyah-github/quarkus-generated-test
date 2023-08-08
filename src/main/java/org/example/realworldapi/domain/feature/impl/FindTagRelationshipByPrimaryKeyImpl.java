package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.FindTagRelationshipByPrimaryKey;
import org.example.realworldapi.domain.exception.TagRelationshipNotFoundException;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.TagRelationshipRepository;


@AllArgsConstructor
public class FindTagRelationshipByPrimaryKeyImpl implements FindTagRelationshipByPrimaryKey {

	private final TagRelationshipRepository tagRelationshipRepository;

	@Override
	public TagRelationship handle(String articleId, String tagId) {
		return tagRelationshipRepository.findTagRelationshipByPrimaryKey(articleId, tagId)
    			.orElseThrow(TagRelationshipNotFoundException::new);
	}
}
