package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.domain.model.school.UpdateSchoolInput;


public interface UpdateSchool {
	School handle(UpdateSchoolInput updateSchoolInput);
}
