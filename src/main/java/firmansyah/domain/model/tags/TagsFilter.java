// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.tagRelationship.TagRelationship;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class TagsFilter {
	private final int offset;
	private final int limit;
	private String id;
	private String name;
}
