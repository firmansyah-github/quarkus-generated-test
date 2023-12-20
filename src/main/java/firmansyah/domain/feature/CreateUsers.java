// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.NewUsersInput;

public interface CreateUsers {
	Users handle(NewUsersInput newUsersInput);
}
