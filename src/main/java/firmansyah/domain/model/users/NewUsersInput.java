// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewUsersInput {
	private String bio;
	private String email;
	private String id;
	private String image;
	private String password;
	private String username;
}
