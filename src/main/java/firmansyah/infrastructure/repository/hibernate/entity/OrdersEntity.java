// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.orders.Orders;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "OrdersEntityFactor")
@Table(name = "ORDERS")
public class OrdersEntity {

	@Id
	@Column(name = "order_id")
	private Integer orderId;
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = true)
	private CustomersEntity customersCustomerId;
	@ManyToOne
	@JoinColumn(name = "employee_id", referencedColumnName = "employee_id", nullable = true)
	private EmployeesEntity employeesEmployeeId;
	private LocalDateTime orderDate;
	private LocalDateTime requiredDate;
	private LocalDateTime shippedDate;
	@ManyToOne
	@JoinColumn(name = "ship_via", referencedColumnName = "shipper_id", nullable = true)
	private ShippersEntity shippersShipVia;
	private Double freight;
	private String shipName;
	private String shipAddress;
	private String shipCity;
	private String shipRegion;
	private String shipPostalCode;
	private String shipCountry;
	@OneToMany(mappedBy = "ordersOrderId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> orderDetailsOrderIdEntityList;

	public OrdersEntity(Orders orders ,EmployeesEntity employeesEmployeeIdEntity,ShippersEntity shippersShipViaEntity,CustomersEntity customersCustomerIdEntity) {
		this.orderId = orders.getOrderId();
		update(orders ,employeesEmployeeIdEntity,shippersShipViaEntity,customersCustomerIdEntity);
		
  	}
  	
  	public void update(Orders orders ,EmployeesEntity employeesEmployeeIdEntity,ShippersEntity shippersShipViaEntity,CustomersEntity customersCustomerIdEntity){
		this.customersCustomerId =customersCustomerIdEntity;
		this.employeesEmployeeId =employeesEmployeeIdEntity;
		this.orderDate = orders.getOrderDate();
		this.requiredDate = orders.getRequiredDate();
		this.shippedDate = orders.getShippedDate();
		this.shippersShipVia =shippersShipViaEntity;
		this.freight = orders.getFreight();
		this.shipName = orders.getShipName();
		this.shipAddress = orders.getShipAddress();
		this.shipCity = orders.getShipCity();
		this.shipRegion = orders.getShipRegion();
		this.shipPostalCode = orders.getShipPostalCode();
		this.shipCountry = orders.getShipCountry();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	OrdersEntity that = (OrdersEntity) o;
    	return Objects.equals(orderId, that.orderId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(orderId);
  	}
}
