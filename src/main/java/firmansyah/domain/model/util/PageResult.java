// created by the factor : Dec 9, 2023, 8:25:21 AM  
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
