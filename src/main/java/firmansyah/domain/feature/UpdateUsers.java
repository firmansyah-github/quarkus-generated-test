// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UpdateUsersInput;


public interface UpdateUsers {
	Users handle(UpdateUsersInput updateUsersInput);
}
