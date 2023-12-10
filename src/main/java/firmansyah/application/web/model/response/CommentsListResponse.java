// created by the factor : Dec 11, 2023, 5:57:49 AM  
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
public class CommentsListResponse {

	private List<CommentsResponse> comments;
	private long commentsCount;

	public CommentsListResponse(List<CommentsResponse> comments, long commentsCount) {
		this.comments = comments;
		this.commentsCount = commentsCount;
	}
}
