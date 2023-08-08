package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.users.Users;
import org.example.realworldapi.application.web.resource.abs.ResourceFilter;
import org.example.realworldapi.domain.model.util.PageResult;

public interface FindUsersByFilter {
	PageResult<Users> handle(ResourceFilter resourceFilter);
}