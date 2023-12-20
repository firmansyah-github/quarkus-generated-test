// created by the factor : Jan 29, 2024, 10:05:08 AM  
package firmansyah.domain.model.tags;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;
            




@Data
@AllArgsConstructor
public class NewTagsInput {
	private String id;
	private String name;
}
