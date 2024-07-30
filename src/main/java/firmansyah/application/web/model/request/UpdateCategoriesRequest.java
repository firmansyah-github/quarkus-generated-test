// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.application.web.model.request;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import firmansyah.domain.model.categories.UpdateCategoriesInput;
import firmansyah.infrastructure.web.validation.constraint.AtLeastOneFieldMustBeNotNull;

            
import firmansyah.domain.model.constants.ValidationMessages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;




@Getter
@Setter
@NoArgsConstructor
@JsonRootName("categories")
@AtLeastOneFieldMustBeNotNull
@RegisterForReflection
public class UpdateCategoriesRequest {

	@NotNull(message = ValidationMessages.CATEGORIES_CATEGORYID_MUST_BE_NOT_BLANK)
	@Max(5)
	private Integer categoryId;
	@NotBlank(message = ValidationMessages.CATEGORIES_CATEGORYNAME_MUST_BE_NOT_BLANK)
	@Size(message = ValidationMessages.CATEGORIES_CATEGORYNAME_MAX_LENGTH, max = 15)
	private String categoryName;
	@Size(message = ValidationMessages.CATEGORIES_DESCRIPTION_MAX_LENGTH, max = 2147483647)
	private String description;
	private byte[] picture;

	public UpdateCategoriesInput toUpdateCategoriesInput() {
		return new UpdateCategoriesInput(
    		this.categoryId, this.categoryName, this.description, this.picture
		);
  }
}
