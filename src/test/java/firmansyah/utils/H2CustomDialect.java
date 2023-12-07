// created by the factor : Dec 7, 2023, 4:03:00 PM  
package firmansyah.utils;

import org.hibernate.dialect.H2Dialect;

import java.sql.Types;

public class H2CustomDialect extends H2Dialect {

  public H2CustomDialect() {
    super();
    //registerColumnType(Types.BINARY, "varbinary");
  }
}
