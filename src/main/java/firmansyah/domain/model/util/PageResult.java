// created by the factor : Dec 11, 2023, 6:10:51 PM  
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
