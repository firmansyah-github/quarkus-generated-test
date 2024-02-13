// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.model.util;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageResult<T> {
  	private List<T> result;
  	private long total;
}
