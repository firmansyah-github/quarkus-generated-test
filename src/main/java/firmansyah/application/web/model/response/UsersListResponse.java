// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.application.web.model.response;

import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@RegisterForReflection
public class UsersListResponse {

	private List<UsersResponse> users;
	private long usersCount;

	public UsersListResponse(List<UsersResponse> users, long usersCount) {
		this.users = users;
		this.usersCount = usersCount;
	}
}
