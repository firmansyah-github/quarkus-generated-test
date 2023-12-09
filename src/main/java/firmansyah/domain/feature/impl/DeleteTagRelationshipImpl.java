// created by the factor : Dec 9, 2023, 9:19:14 AM  
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
