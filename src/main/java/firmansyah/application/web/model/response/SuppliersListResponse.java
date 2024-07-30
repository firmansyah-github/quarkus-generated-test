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
public class SuppliersListResponse {

	private List<SuppliersResponse> suppliers;
	private long suppliersCount;

	public SuppliersListResponse(List<SuppliersResponse> suppliers, long suppliersCount) {
		this.suppliers = suppliers;
		this.suppliersCount = suppliersCount;
	}
}
