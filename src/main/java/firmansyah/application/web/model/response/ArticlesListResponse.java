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
public class ArticlesListResponse {

	private List<ArticlesResponse> articles;
	private long articlesCount;

	public ArticlesListResponse(List<ArticlesResponse> articles, long articlesCount) {
		this.articles = articles;
		this.articlesCount = articlesCount;
	}
}
