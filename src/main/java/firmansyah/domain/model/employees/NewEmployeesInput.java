// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employees;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            

import java.time.LocalDateTime;



@Data
@AllArgsConstructor
public class NewEmployeesInput {
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
	private Integer reportsTo;
	private String photoPath;
}
