// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.customerDemographics.CustomerDemographics;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CustomerDemographicsEntityFactor")
@Table(name = "CUSTOMER_DEMOGRAPHICS")
public class CustomerDemographicsEntity {

	@Id
	@Column(name = "customer_type_id")
	private String customerTypeId;
	private String customerDesc;
	@OneToMany(mappedBy = "customerDemographicsCustomerTypeId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<CustomerCustomerDemoEntity> customerCustomerDemoCustomerTypeIdEntityList;

	public CustomerDemographicsEntity(CustomerDemographics customerDemographics ) {
		this.customerTypeId = customerDemographics.getCustomerTypeId();
		update(customerDemographics );
		
  	}
  	
  	public void update(CustomerDemographics customerDemographics ){
		this.customerDesc = customerDemographics.getCustomerDesc();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	CustomerDemographicsEntity that = (CustomerDemographicsEntity) o;
    	return Objects.equals(customerTypeId, that.customerTypeId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(customerTypeId);
  	}
}
