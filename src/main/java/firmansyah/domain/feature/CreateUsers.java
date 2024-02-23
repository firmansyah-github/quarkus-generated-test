// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.NewUsersInput;

public interface CreateUsers {
	Users handle(NewUsersInput newUsersInput);
}
