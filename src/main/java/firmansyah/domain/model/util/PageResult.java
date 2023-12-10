// created by the factor : Dec 11, 2023, 5:57:49 AM  
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
