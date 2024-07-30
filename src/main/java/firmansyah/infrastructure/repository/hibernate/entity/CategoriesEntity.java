// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.categories.Categories;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "CategoriesEntityFactor")
@Table(name = "CATEGORIES")
public class CategoriesEntity {

	@Id
	@Column(name = "category_id")
	private Integer categoryId;
	private String categoryName;
	private String description;
	private byte[] picture;
	@OneToMany(mappedBy = "categoriesCategoryId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductsEntity> productsCategoryIdEntityList;

	public CategoriesEntity(Categories categories ) {
		this.categoryId = categories.getCategoryId();
		update(categories );
		
  	}
  	
  	public void update(Categories categories ){
		this.categoryName = categories.getCategoryName();
		this.description = categories.getDescription();
		this.picture = categories.getPicture();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	CategoriesEntity that = (CategoriesEntity) o;
    	return Objects.equals(categoryId, that.categoryId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(categoryId);
  	}
}
