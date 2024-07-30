// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.customerCustomerDemo.CustomerCustomerDemo;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CustomerCustomerDemoEntityFactor")
@Table(name = "CUSTOMER_CUSTOMER_DEMO")
public class CustomerCustomerDemoEntity {

	@EmbeddedId 
	private CustomerCustomerDemoEntityKey primaryKey;
	@ManyToOne
	@JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
	private CustomersEntity customersCustomerId;
	@ManyToOne
	@JoinColumn(name = "customer_type_id", referencedColumnName = "customer_type_id", insertable = false, updatable = false)
	private CustomerDemographicsEntity customerDemographicsCustomerTypeId;

	public CustomerCustomerDemoEntity(CustomerCustomerDemo customerCustomerDemo ,CustomersEntity customersCustomerIdEntity,CustomerDemographicsEntity customerDemographicsCustomerTypeIdEntity) {
		final var customerCustomerDemoEntityKey = new CustomerCustomerDemoEntityKey();
		customerCustomerDemoEntityKey.setCustomersCustomerId(customersCustomerIdEntity);
		customerCustomerDemoEntityKey.setCustomerDemographicsCustomerTypeId(customerDemographicsCustomerTypeIdEntity);
		this.primaryKey = customerCustomerDemoEntityKey;
		update(customerCustomerDemo ,customersCustomerIdEntity,customerDemographicsCustomerTypeIdEntity);
		
  	}
  	
  	public void update(CustomerCustomerDemo customerCustomerDemo ,CustomersEntity customersCustomerIdEntity,CustomerDemographicsEntity customerDemographicsCustomerTypeIdEntity){
		this.customersCustomerId =customersCustomerIdEntity;
		this.customerDemographicsCustomerTypeId =customerDemographicsCustomerTypeIdEntity;
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	CustomerCustomerDemoEntity that = (CustomerCustomerDemoEntity) o;
    	return Objects.equals(primaryKey, that.primaryKey);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(customersCustomerId, customerDemographicsCustomerTypeId);
  	}
}
