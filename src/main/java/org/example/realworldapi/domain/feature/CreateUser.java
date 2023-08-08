package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.user.NewUserInput;
import org.example.realworldapi.domain.model.user.User;

public interface CreateUser {
	User handle(NewUserInput newUserInput);
}
