// created by the factor : May 30, 2024, 6:48:44 AM  
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
public class RegionListResponse {

	private List<RegionResponse> region;
	private long regionCount;

	public RegionListResponse(List<RegionResponse> region, long regionCount) {
		this.region = region;
		this.regionCount = regionCount;
	}
}
