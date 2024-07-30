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
public class CustomerCustomerDemoEntityKey implements Serializable {

    @ManyToOne 
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id")
    private CustomersEntity customersCustomerId;
    @ManyToOne 
    @JoinColumn(name = "customer_type_id", referencedColumnName = "customer_type_id")
    private CustomerDemographicsEntity customerDemographicsCustomerTypeId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	CustomerCustomerDemoEntityKey that = (CustomerCustomerDemoEntityKey) o;
    	return Objects.equals(customersCustomerId, that.customersCustomerId) && Objects.equals(customerDemographicsCustomerTypeId, that.customerDemographicsCustomerTypeId);
  	}

  	@Override
  	public int hashCode() {
    	return Objects.hash(customersCustomerId, customerDemographicsCustomerTypeId);
  	}

}
