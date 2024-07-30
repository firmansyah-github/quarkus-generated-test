// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.infrastructure.repository.hibernate.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.employees.Employees;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "EmployeesEntityFactor")
@Table(name = "EMPLOYEES")
public class EmployeesEntity {

	@Id
	@Column(name = "employee_id")
	private Integer employeeId;
	private String lastName;
	private String firstName;
	private String title;
	private String titleOfCourtesy;
	private LocalDateTime birthDate;
	private LocalDateTime hireDate;
	private String address;
	private String city;
	private String region;
	private String postalCode;
	private String country;
	private String homePhone;
	private String extension;
	private byte[] photo;
	private String notes;
	@ManyToOne
	@JoinColumn(name = "reports_to", referencedColumnName = "employee_id", nullable = true)
	private EmployeesEntity employeesReportsTo;
	private String photoPath;
	@OneToMany(mappedBy = "employeesEmployeeId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeTerritoriesEntity> employeeTerritoriesEmployeeIdEntityList;
	@OneToMany(mappedBy = "employeesEmployeeId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrdersEntity> ordersEmployeeIdEntityList;
	@OneToMany(mappedBy = "employeesReportsTo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeesEntity> employeesReportsToEntityList;

	public EmployeesEntity(Employees employees ,EmployeesEntity employeesReportsToEntity) {
		this.employeeId = employees.getEmployeeId();
		update(employees ,employeesReportsToEntity);
		
  	}
  	
  	public void update(Employees employees ,EmployeesEntity employeesReportsToEntity){
		this.lastName = employees.getLastName();
		this.firstName = employees.getFirstName();
		this.title = employees.getTitle();
		this.titleOfCourtesy = employees.getTitleOfCourtesy();
		this.birthDate = employees.getBirthDate();
		this.hireDate = employees.getHireDate();
		this.address = employees.getAddress();
		this.city = employees.getCity();
		this.region = employees.getRegion();
		this.postalCode = employees.getPostalCode();
		this.country = employees.getCountry();
		this.homePhone = employees.getHomePhone();
		this.extension = employees.getExtension();
		this.photo = employees.getPhoto();
		this.notes = employees.getNotes();
		this.employeesReportsTo =employeesReportsToEntity;
		this.photoPath = employees.getPhotoPath();
		
  	}

  

  	@Override
  	public boolean equals(Object o) {
		if (this == o) return true;

    	if (o == null || getClass() != o.getClass()) return false;

    	EmployeesEntity that = (EmployeesEntity) o;
    	return Objects.equals(employeeId, that.employeeId);
  	}

  	@Override
  	public int hashCode() {
		return Objects.hash(employeeId);
  	}
}
