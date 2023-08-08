package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tags.Tags;
import org.example.realworldapi.domain.model.tags.UpdateTagsInput;


public interface UpdateTags {
	Tags handle(UpdateTagsInput updateTagsInput);
}
