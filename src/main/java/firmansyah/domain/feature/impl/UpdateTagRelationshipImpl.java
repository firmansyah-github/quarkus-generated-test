// created by the factor : Dec 11, 2023, 6:10:51 PM  
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
	private final FindTagsByPrimaryKey findTagsTagIdByPrimaryKey;
	private final FindArticlesByPrimaryKey findArticlesArticleIdByPrimaryKey;
	

	@Override
	public TagRelationship handle(UpdateTagRelationshipInput updateTagRelationshipInput) {
		var tagRelationship = findTagRelationshipByPrimaryKey.handle(updateTagRelationshipInput.getArticleId(), updateTagRelationshipInput.getTagId());
		
    	tagRelationship =
			tagRelationshipBuilder.build(findTagsTagIdByPrimaryKey.handle(updateTagRelationshipInput.getTagId()),
					findArticlesArticleIdByPrimaryKey.handle(updateTagRelationshipInput.getArticleId()));
		tagRelationshipRepository.update(tagRelationship);
    
		return tagRelationship;
	}
}
