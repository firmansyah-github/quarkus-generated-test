// created by the factor : Dec 9, 2023, 8:25:21 AM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UpdateUsersInput;


public interface UpdateUsers {
	Users handle(UpdateUsersInput updateUsersInput);
}
