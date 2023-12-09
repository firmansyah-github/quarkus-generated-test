// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
