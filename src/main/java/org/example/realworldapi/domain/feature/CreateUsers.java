package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.domain.model.users.NewUsersInput;

public interface CreateUsers {
	Users handle(NewUsersInput newUsersInput);
}
