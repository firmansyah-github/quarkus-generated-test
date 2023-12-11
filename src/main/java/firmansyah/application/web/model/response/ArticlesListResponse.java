// created by the factor : Dec 11, 2023, 6:10:51 PM  
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
