// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.DeleteTags;
import firmansyah.domain.model.tags.TagsRepository;


@AllArgsConstructor
public class DeleteTagsImpl implements DeleteTags {

	private final TagsRepository tagsRepository;

  	@Override
	public boolean handle(String id) {
		return tagsRepository.delete(id);
	}
}
