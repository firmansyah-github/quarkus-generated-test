// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.shippers.Shippers;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ShippersEntityFactor")
@Table(name = "SHIPPERS")
public class ShippersEntity {

	@Id
	@Column(name = "shipper_id")
	private Integer shipperId;
	private String companyName;
	private String phone;
	@OneToMany(mappedBy = "shippersShipVia", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrdersEntity> ordersShipViaEntityList;

	public ShippersEntity(Shippers shippers ) {
		this.shipperId = shippers.getShipperId();
		update(shippers );
		
  	}
  	
  	public void update(Shippers shippers ){
		this.companyName = shippers.getCompanyName();
		this.phone = shippers.getPhone();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	ShippersEntity that = (ShippersEntity) o;
    	return Objects.equals(shipperId, that.shipperId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(shipperId);
  	}
}
