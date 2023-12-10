// created by the factor : Dec 11, 2023, 5:57:49 AM  
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
