// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.feature.impl;

import lombok.AllArgsConstructor;
import firmansyah.domain.feature.FindTagsByPrimaryKey;
import firmansyah.domain.exception.TagsNotFoundException;
import firmansyah.domain.model.tags.Tags;
import firmansyah.domain.model.tags.TagsRepository;


@AllArgsConstructor
public class FindTagsByPrimaryKeyImpl implements FindTagsByPrimaryKey {

	private final TagsRepository tagsRepository;

	@Override
	public Tags handle(String id) {
		return tagsRepository.findTagsByPrimaryKey(id)
    			.orElseThrow(TagsNotFoundException::new);
	}
}
