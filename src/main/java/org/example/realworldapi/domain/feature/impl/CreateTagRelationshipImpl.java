package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;
import org.example.realworldapi.domain.feature.CreateTagRelationship;
import org.example.realworldapi.domain.model.tagRelationship.*;
import org.example.realworldapi.domain.exception.TagRelationshipAlreadyExistsException;
import org.example.realworldapi.domain.feature.*;


@AllArgsConstructor
public class CreateTagRelationshipImpl implements CreateTagRelationship {

	private final TagRelationshipRepository tagRelationshipRepository;
	private final TagRelationshipModelBuilder tagRelationshipBuilder;
	private final FindTagsByPrimaryKey findTagsTagIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput) {
		final var tagRelationship =
			tagRelationshipBuilder.build(findTagsTagIdByPrimaryKey.handle(newTagRelationshipInput.getTagId()),
					findArticlesArticleIdByPrimaryKey.handle(newTagRelationshipInput.getArticleId()));
		
		if(tagRelationshipRepository.findTagRelationshipByPrimaryKey(tagRelationship.getArticlesArticleId().getId(), tagRelationship.getTagsTagId().getId()).isPresent()) {
			throw new TagRelationshipAlreadyExistsException();
		} else {
			tagRelationshipRepository.save(tagRelationship);
		}
   
		return tagRelationship;
	}
}
