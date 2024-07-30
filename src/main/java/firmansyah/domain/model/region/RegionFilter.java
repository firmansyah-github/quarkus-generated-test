// created by the factor : May 30, 2024, 6:48:44 AM  
package firmansyah.domain.model.region;

import lombok.AllArgsConstructor;
import lombok.Data;

            


import firmansyah.domain.model.territories.Territories;
import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
public class RegionFilter {
	private final int offset;
	private final int limit;
	private Integer regionId;
	private String regionDescription;
}
