// created by the factor : Jan 29, 2024, 10:05:08 AM  
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
