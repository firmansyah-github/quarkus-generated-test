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
public class ProductsListResponse {

	private List<ProductsResponse> products;
	private long productsCount;

	public ProductsListResponse(List<ProductsResponse> products, long productsCount) {
		this.products = products;
		this.productsCount = productsCount;
	}
}
