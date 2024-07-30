// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderDetailsEntityKey implements Serializable {

    @ManyToOne 
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private OrdersEntity ordersOrderId;
    @ManyToOne 
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private ProductsEntity productsProductId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	OrderDetailsEntityKey that = (OrderDetailsEntityKey) o;
    	return Objects.equals(ordersOrderId, that.ordersOrderId) && Objects.equals(productsProductId, that.productsProductId);
  	}

  	@Override
  	public int hashCode() {
    	return Objects.hash(ordersOrderId, productsProductId);
  	}

}
