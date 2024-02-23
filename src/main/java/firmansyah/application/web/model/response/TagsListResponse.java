// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.application.web.model.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class TagsListResponse {

	private List<TagsResponse> tags;
	private long tagsCount;

	public TagsListResponse(List<TagsResponse> tags, long tagsCount) {
		this.tags = tags;
		this.tagsCount = tagsCount;
	}
}
