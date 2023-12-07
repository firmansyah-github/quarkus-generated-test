// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.domain.feature;

import firmansyah.domain.model.users.Users;
import firmansyah.domain.model.users.UpdateUsersInput;


public interface UpdateUsers {
	Users handle(UpdateUsersInput updateUsersInput);
}
