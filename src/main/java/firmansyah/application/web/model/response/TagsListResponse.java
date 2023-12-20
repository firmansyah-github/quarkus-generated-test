// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
