// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.employees;

import lombok.AllArgsConstructor;
import lombok.Data;

            

import java.time.LocalDateTime;

import firmansyah.domain.model.employeeTerritories.EmployeeTerritories;
import firmansyah.domain.model.orders.Orders;
import firmansyah.domain.model.employees.Employees;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class EmployeesFilter {
	private final int offset;
	private final int limit;
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
