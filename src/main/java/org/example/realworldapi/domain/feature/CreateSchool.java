package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.school.School;
import org.example.realworldapi.domain.model.school.NewSchoolInput;

public interface CreateSchool {
	School handle(NewSchoolInput newSchoolInput);
}
