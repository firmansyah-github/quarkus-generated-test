// created by the factor : Dec 9, 2023, 9:19:14 AM  
package firmansyah.utils;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

public class H2CustomDialect extends H2Dialect {

  public H2CustomDialect() {
    super();
    //registerColumnType(Types.BINARY, "varbinary");
  }
}
