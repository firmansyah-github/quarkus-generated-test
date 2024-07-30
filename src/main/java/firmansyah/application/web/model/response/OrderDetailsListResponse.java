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
public class OrderDetailsListResponse {

	private List<OrderDetailsResponse> orderDetails;
	private long orderDetailsCount;

	public OrderDetailsListResponse(List<OrderDetailsResponse> orderDetails, long orderDetailsCount) {
		this.orderDetails = orderDetails;
		this.orderDetailsCount = orderDetailsCount;
	}
}
