package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.users.UpdateUsersInput;


public interface UpdateUsers {
	Users handle(UpdateUsersInput updateUsersInput);
}
