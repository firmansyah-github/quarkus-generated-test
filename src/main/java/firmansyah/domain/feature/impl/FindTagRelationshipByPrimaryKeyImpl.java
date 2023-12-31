// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindTagRelationshipByPrimaryKey;
import firmansyah.domain.exception.TagRelationshipNotFoundException;
import firmansyah.domain.model.tagRelationship.TagRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationshipRepository;


@AllArgsConstructor
public class FindTagRelationshipByPrimaryKeyImpl implements FindTagRelationshipByPrimaryKey {

	private final TagRelationshipRepository tagRelationshipRepository;

	@Override
	public TagRelationship handle(String articleId, String tagId) {
		return tagRelationshipRepository.findTagRelationshipByPrimaryKey(articleId, tagId)
    			.orElseThrow(TagRelationshipNotFoundException::new);
	}
}
