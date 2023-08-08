package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.domain.model.tags.NewTagsInput;

public interface CreateTags {
	Tags handle(NewTagsInput newTagsInput);
}
