// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
