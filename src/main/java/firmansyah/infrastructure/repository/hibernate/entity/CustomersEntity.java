// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.customers.Customers;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CustomersEntityFactor")
@Table(name = "CUSTOMERS")
public class CustomersEntity {

	@Id
	@Column(name = "customer_id")
	private String customerId;
	private String companyName;
	private String contactName;
	private String contactTitle;
	private String address;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	private String phone;
	private String fax;
	@OneToMany(mappedBy = "customersCustomerId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrdersEntity> ordersCustomerIdEntityList;
	@OneToMany(mappedBy = "customersCustomerId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CustomerCustomerDemoEntity> customerCustomerDemoCustomerIdEntityList;

	public CustomersEntity(Customers customers ) {
		this.customerId = customers.getCustomerId();
		update(customers );
		
  	}
  	
  	public void update(Customers customers ){
		this.companyName = customers.getCompanyName();
		this.contactName = customers.getContactName();
		this.contactTitle = customers.getContactTitle();
		this.address = customers.getAddress();
		this.city = customers.getCity();
		this.region = customers.getRegion();
		this.postalCode = customers.getPostalCode();
		this.country = customers.getCountry();
		this.phone = customers.getPhone();
		this.fax = customers.getFax();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	CustomersEntity that = (CustomersEntity) o;
    	return Objects.equals(customerId, that.customerId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(customerId);
  	}
}
