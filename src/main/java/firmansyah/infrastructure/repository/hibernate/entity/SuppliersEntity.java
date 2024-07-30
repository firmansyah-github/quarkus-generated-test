// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.suppliers.Suppliers;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "SuppliersEntityFactor")
@Table(name = "SUPPLIERS")
public class SuppliersEntity {

	@Id
	@Column(name = "supplier_id")
	private Integer supplierId;
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
	private String homepage;
	@OneToMany(mappedBy = "suppliersSupplierId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductsEntity> productsSupplierIdEntityList;

	public SuppliersEntity(Suppliers suppliers ) {
		this.supplierId = suppliers.getSupplierId();
		update(suppliers );
		
  	}
  	
  	public void update(Suppliers suppliers ){
		this.companyName = suppliers.getCompanyName();
		this.contactName = suppliers.getContactName();
		this.contactTitle = suppliers.getContactTitle();
		this.address = suppliers.getAddress();
		this.city = suppliers.getCity();
		this.region = suppliers.getRegion();
		this.postalCode = suppliers.getPostalCode();
		this.country = suppliers.getCountry();
		this.phone = suppliers.getPhone();
		this.fax = suppliers.getFax();
		this.homepage = suppliers.getHomepage();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	SuppliersEntity that = (SuppliersEntity) o;
    	return Objects.equals(supplierId, that.supplierId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(supplierId);
  	}
}
