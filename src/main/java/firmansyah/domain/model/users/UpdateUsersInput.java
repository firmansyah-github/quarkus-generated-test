// created by the factor : Dec 11, 2023, 6:10:51 PM  
package firmansyah.domain.model.users;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            





@Data
@AllArgsConstructor
public class UpdateUsersInput {
	private String bio;
	private String email;
	private String id;
	private String image;
	private String password;
	private String username;
}
