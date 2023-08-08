package org.example.realworldapi.application.web.model.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class SchoolListResponse {

	private List<SchoolResponse> school;
	private long schoolCount;

	public SchoolListResponse(List<SchoolResponse> school, long schoolCount) {
		this.school = school;
		this.schoolCount = schoolCount;
	}
}
