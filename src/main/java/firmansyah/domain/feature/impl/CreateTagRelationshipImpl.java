// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.CreateTagRelationship;
import firmansyah.domain.model.tagRelationship.*;
import firmansyah.domain.exception.TagRelationshipAlreadyExistsException;
import firmansyah.domain.feature.*;



@AllArgsConstructor
public class CreateTagRelationshipImpl implements CreateTagRelationship {

	private final TagRelationshipRepository tagRelationshipRepository;
	private final TagRelationshipModelBuilder tagRelationshipBuilder;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	private final FindTagsByPrimaryKey findTagsTagIdByPrimaryKey;
	

	@Override
	public TagRelationship handle(NewTagRelationshipInput newTagRelationshipInput) {
		final var tagRelationship =
			tagRelationshipBuilder.build(findArticlesArticleIdByPrimaryKey.handle(newTagRelationshipInput.getArticleId()),
					findTagsTagIdByPrimaryKey.handle(newTagRelationshipInput.getTagId()));
		
		if(tagRelationshipRepository.findTagRelationshipByPrimaryKey(tagRelationship.getArticlesArticleId().getId(), tagRelationship.getTagsTagId().getId()).isPresent()) {
			throw new TagRelationshipAlreadyExistsException();
		} else {
			tagRelationshipRepository.save(tagRelationship);
		}
   
		return tagRelationship;
	}
}
