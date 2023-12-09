// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.NewUsersInput;

public interface CreateUsers {
	Users handle(NewUsersInput newUsersInput);
}
