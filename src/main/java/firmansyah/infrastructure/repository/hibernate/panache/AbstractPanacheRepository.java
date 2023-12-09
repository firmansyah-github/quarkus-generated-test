// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.infrastructure.repository.hibernate.panache;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import firmansyah.infrastructure.repository.hibernate.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class AbstractPanacheRepository<ENTITY, ID> implements PanacheRepositoryBase<ENTITY, ID> {

 	protected UsersEntity findUserEntityById(String id) {
		return getEntityManager().find(UsersEntity.class, id);
	}

	protected boolean isNotEmpty(List<?> list) {
		return list != null && !list.isEmpty();
	}

	protected List<String> toUpperCase(List<String> subjectList) {
		return subjectList.stream().map(String::toUpperCase).collect(Collectors.toList());
	}
}
