// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.employees.Employees;
            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@JsonRootName("employees")
@RegisterForReflection
public class EmployeesResponse {

	@NotNull(message = ValidationMessages.EMPLOYEES_EMPLOYEEID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer employeeId;
	@NotBlank(message = ValidationMessages.EMPLOYEES_LASTNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.EMPLOYEES_LASTNAME_MAX_LENGTH, max = 20)
	private String lastName;
	@NotBlank(message = ValidationMessages.EMPLOYEES_FIRSTNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.EMPLOYEES_FIRSTNAME_MAX_LENGTH, max = 10)
	private String firstName;
	@Size(message = ValidationMessages.EMPLOYEES_TITLE_MAX_LENGTH, max = 30)
	private String title;
	@Size(message = ValidationMessages.EMPLOYEES_TITLEOFCOURTESY_MAX_LENGTH, max = 25)
	private String titleOfCourtesy;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime birthDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
	private LocalDateTime hireDate;
	@Size(message = ValidationMessages.EMPLOYEES_ADDRESS_MAX_LENGTH, max = 60)
	private String address;
	@Size(message = ValidationMessages.EMPLOYEES_CITY_MAX_LENGTH, max = 15)
	private String city;
	@Size(message = ValidationMessages.EMPLOYEES_REGION_MAX_LENGTH, max = 15)
	private String region;
	@Size(message = ValidationMessages.EMPLOYEES_POSTALCODE_MAX_LENGTH, max = 10)
	private String postalCode;
	@Size(message = ValidationMessages.EMPLOYEES_COUNTRY_MAX_LENGTH, max = 15)
	private String country;
	@Size(message = ValidationMessages.EMPLOYEES_HOMEPHONE_MAX_LENGTH, max = 24)
	private String homePhone;
	@Size(message = ValidationMessages.EMPLOYEES_EXTENSION_MAX_LENGTH, max = 4)
	private String extension;
	private byte[] photo;
	@Size(message = ValidationMessages.EMPLOYEES_NOTES_MAX_LENGTH, max = 2147483647)
	private String notes;
	@Size(message = ValidationMessages.EMPLOYEES_PHOTOPATH_MAX_LENGTH, max = 255)
	private String photoPath;
	private EmployeesResponse employeesReportsToResponse;
	

	public EmployeesResponse(Employees employees,  EmployeesResponse employeesReportsToResponse) {
								
		this.employeeId = employees.getEmployeeId();
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
		this.photoPath = employees.getPhotoPath();
		this.employeesReportsToResponse =employeesReportsToResponse;
		

	}
    
	public EmployeesResponse(boolean isFlag, Employees employees) {
		if(isFlag){
			this.employeeId = employees.getEmployeeId();
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
			this.photoPath = employees.getPhotoPath();
			
		}
	}
  
}
