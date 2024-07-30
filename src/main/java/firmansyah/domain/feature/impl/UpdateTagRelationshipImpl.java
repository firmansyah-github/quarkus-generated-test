// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.model.tagRelationship.*;
import firmansyah.domain.feature.*;
import firmansyah.domain.feature.*;

@AllArgsConstructor
public class UpdateTagRelationshipImpl implements UpdateTagRelationship {

	private final TagRelationshipRepository tagRelationshipRepository;
	private final TagRelationshipModelBuilder tagRelationshipBuilder;
	private final FindTagRelationshipByPrimaryKey findTagRelationshipByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	private final FindTagsByPrimaryKey findTagsTagIdByPrimaryKey;
	

	@Override
	public TagRelationship handle(UpdateTagRelationshipInput updateTagRelationshipInput) {
		var tagRelationship = findTagRelationshipByPrimaryKey.handle(updateTagRelationshipInput.getArticleId(), updateTagRelationshipInput.getTagId());
		
    	tagRelationship =
			tagRelationshipBuilder.build(findArticlesArticleIdByPrimaryKey.handle(updateTagRelationshipInput.getArticleId()),
					findTagsTagIdByPrimaryKey.handle(updateTagRelationshipInput.getTagId()));
		tagRelationshipRepository.update(tagRelationship);
    
		return tagRelationship;
	}
}
