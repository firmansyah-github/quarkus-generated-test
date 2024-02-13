// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteTagRelationship;
import firmansyah.domain.model.tagRelationship.TagRelationshipRepository;


@AllArgsConstructor
public class DeleteTagRelationshipImpl implements DeleteTagRelationship {

	private final TagRelationshipRepository tagRelationshipRepository;

  	@Override
	public boolean handle(String articleId, String tagId) {
		return tagRelationshipRepository.delete(articleId, tagId);
	}
}
