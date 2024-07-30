// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.orderDetails.OrderDetails;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "OrderDetailsEntityFactor")
@Table(name = "ORDER_DETAILS")
public class OrderDetailsEntity {

	@EmbeddedId 
	private OrderDetailsEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "order_id", insertable = false, updatable = false)
	private OrdersEntity ordersOrderId;
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
	private ProductsEntity productsProductId;
	private Double unitPrice;
	private Integer quantity;
	private Double discount;

	public OrderDetailsEntity(OrderDetails orderDetails ,OrdersEntity ordersOrderIdEntity,ProductsEntity productsProductIdEntity) {
		final var orderDetailsEntityKey = new OrderDetailsEntityKey();
		orderDetailsEntityKey.setOrdersOrderId(ordersOrderIdEntity);
		orderDetailsEntityKey.setProductsProductId(productsProductIdEntity);
		this.primaryKey = orderDetailsEntityKey;
		update(orderDetails ,ordersOrderIdEntity,productsProductIdEntity);
		
  	}
  	
  	public void update(OrderDetails orderDetails ,OrdersEntity ordersOrderIdEntity,ProductsEntity productsProductIdEntity){
		this.ordersOrderId =ordersOrderIdEntity;
		this.productsProductId =productsProductIdEntity;
		this.unitPrice = orderDetails.getUnitPrice();
		this.quantity = orderDetails.getQuantity();
		this.discount = orderDetails.getDiscount();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	OrderDetailsEntity that = (OrderDetailsEntity) o;
    	return Objects.equals(primaryKey, that.primaryKey);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(ordersOrderId, productsProductId);
  	}
}
