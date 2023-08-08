package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.users.Users;
            






public interface FindUsersByPrimaryKey {
	Users handle(String id);
}

