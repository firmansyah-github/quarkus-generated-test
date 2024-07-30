// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.products.Products;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "ProductsEntityFactor")
@Table(name = "PRODUCTS")
public class ProductsEntity {

	@Id
	@Column(name = "product_id")
	private Integer productId;
	private String productName;
	@ManyToOne
	@JoinColumn(name = "supplier_id", referencedColumnName = "supplier_id", nullable = true)
	private SuppliersEntity suppliersSupplierId;
	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = true)
	private CategoriesEntity categoriesCategoryId;
	private String quantityPerUnit;
	private Double unitPrice;
	private Integer unitsInStock;
	private Integer unitsOnOrder;
	private Integer reorderLevel;
	private Integer discontinued;
	@OneToMany(mappedBy = "productsProductId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderDetailsEntity> orderDetailsProductIdEntityList;

	public ProductsEntity(Products products ,CategoriesEntity categoriesCategoryIdEntity,SuppliersEntity suppliersSupplierIdEntity) {
		this.productId = products.getProductId();
		update(products ,categoriesCategoryIdEntity,suppliersSupplierIdEntity);
		
  	}
  	
  	public void update(Products products ,CategoriesEntity categoriesCategoryIdEntity,SuppliersEntity suppliersSupplierIdEntity){
		this.productName = products.getProductName();
		this.suppliersSupplierId =suppliersSupplierIdEntity;
		this.categoriesCategoryId =categoriesCategoryIdEntity;
		this.quantityPerUnit = products.getQuantityPerUnit();
		this.unitPrice = products.getUnitPrice();
		this.unitsInStock = products.getUnitsInStock();
		this.unitsOnOrder = products.getUnitsOnOrder();
		this.reorderLevel = products.getReorderLevel();
		this.discontinued = products.getDiscontinued();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	ProductsEntity that = (ProductsEntity) o;
    	return Objects.equals(productId, that.productId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(productId);
  	}
}
