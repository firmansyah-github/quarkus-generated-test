// created by the factor : Feb 23, 2024, 6:45:22 AM  
package firmansyah.application.web.model.response;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@JsonRootName("errors")
@RegisterForReflection
public class ErrorResponse {

	private List<String> body;

	public ErrorResponse() {
		this.body = new LinkedList<>();
  	}

  	public ErrorResponse(String error) {
    	this();
    	this.body.add(error);
  	}

  	public ErrorResponse(List<String> errors) {
    	this.body = errors;
  	}
}
